import java.util.*;
import java.io.*;
import java.time.*;

public class FlashcardDisplayer{
    private FlashcardPriorityQueue queue = new FlashcardPriorityQueue();
    /**
    * Creates a flashcard displayer with the flashcards in file.
    * File has one flashcard per line. On each line, the date the flashcard
    * should next be shown is first (format: YYYY-MM-DDTHH-MM), followed by a tab,
    * followed by the text for the front of the flashcard, followed by another tab.
    * followed by the text for the back of the flashcard. You can assume that the
    * front/back text does not itself contain tabs. (I.e., a properly formatted file
    * has exactly 2 tabs per line.)
    * In the format above, the time may be omitted, or the time may be more precise
    * (e.g., seconds may be included). The parse method in LocalDateTime can deal
    * with these situations without any changes to your code.
    */
    public FlashcardDisplayer(String file){
        // load flashcard and add them to a FlashcardPriorityQueue
        Scanner scanner = null;
        String line = ""; // variable that holds each line being scanned
        String[] lineArray = null; // array that holds each word in each line
        File flashcardsFile = new File(file);
        try{
            scanner = new Scanner(flashcardsFile);
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                lineArray = line.split("\t"); // split line based on tab character
                Flashcard newFlashcard = new Flashcard(lineArray[0], lineArray[1], lineArray[2]); //create new flashcard with line info
                queue.add(newFlashcard); // add to the priority queue
            }
        }
        catch(Exception e){
            System.out.println("An error occurred trying to load the flashcards.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    /**
    * Writes out all flashcards to a file so that they can be loaded
    * by the FlashcardDisplayer(String file) constructor. Returns true
    * if the file could be written. The FlashcardDisplayer should still
    * have all of the same flashcards after this method is called as it
    * did before the method was called. However, it may be that flashcards
    * with the same exact same next display date are removed in a different order.
    */
    public boolean saveFlashcards(String outFile){
          File file = new File(outFile);
          try{
              FileWriter myWriter = new FileWriter(outFile); // create FileWriter
              Flashcard card = null;
              // while priority queue is not empty, keep polling the highest priority card and write it to the outfile
              while(!queue.isEmpty()){
                  card = queue.poll();
                  myWriter.write(card.getDueDate() + "\t" + card.getFrontText() + "\t" + card.getBackText() + "\n");
              }
              myWriter.close();
              return true;
          }
          catch(IOException e){
              System.out.println("An error occurred while writing to the file.");
              return false;
          }
    }

    /**
    * Displays any flashcards that are currently due to the user, and
    * asks them to report whether they got each card correct. If the
    * card was correct, it is added back to the deck of cards with a new
    * due date that is one day later than the current date and time; if
    * the card was incorrect, it is added back to the card with a new due
    * date that is one minute later than that the current date and time.
    */
    public void displayFlashcards(){
        // if the queue is empty, it means that no cards have been added so one can exit the method.
        if (queue.isEmpty()){
            System.out.println("No cards are waiting to be studied!");
            return;
        }
        else{
            Scanner quizScanner = new Scanner(System.in);
            String input = "";
            Flashcard curCard = null; // current card being quizzed

            while(!queue.isEmpty()){
                curCard = queue.peek();
                if (curCard.getDueDate().compareTo(LocalDateTime.now()) < 0){ // checks if the polled card's due date is before the present or not
                    curCard = queue.poll();
                    System.out.println();
                    System.out.println("Card: ");
                    System.out.println(curCard.getFrontText());
                    System.out.println("[Press return for back of card]");

                    boolean enter = false;
                    // loops until user presses return
                    while (!enter){
                        input = quizScanner.nextLine();
                        if (input.isEmpty()){
                            enter = true;
                        }
                    }
                    System.out.println(curCard.getBackText());
                    System.out.println("Press 1 if you got the card correct and 2 if you got the card incorrect.");
                    // asks for input until the user inputs either 1 or two, and add to heap again with updated time depending on the answer
                    while (!(input.equals("1") || input.equals("2"))){
                        input = quizScanner.nextLine();
                    }
                    // if the user inputs 1, then add the card back to the heap with a due date that is one day from now
                    if (input.equals("1")){
                        curCard.setDueDate(LocalDateTime.now().minusDays(-1));
                        queue.add(curCard);
                    }
                    // if the user inputs 2, then add the card back to the heap with a due date that is one minute from now
                    if (input.equals("2")){
                        curCard.setDueDate(LocalDateTime.now().minusMinutes(-1));
                        queue.add(curCard);
                    }
                }
                else{
                    // the polled flashcard is still not due yet, so exit
                    System.out.println("No cards are waiting to be studied!");
                    return;
                }
            }
        }
    }
    public static void main(String[] args){
        if (args.length != 1){
            System.out.println("Please provide one command line argument with the name of the file.");
        }
        else{
            String filePath = args[0]; // stores file path
            FlashcardDisplayer displayer = new FlashcardDisplayer(filePath); // creates new FlashcardDisplayer
            Scanner scanner = new Scanner(System.in); // creates new scanner
            String option = ""; // stores the command the user wants to execute

            // prompts user for a command until the user inputs "exit"
            while(!option.equals("exit")){
                System.out.println("What would you like to do? The options: quiz, save, and exit");
                option = scanner.nextLine();
                switch(option){
                  case "save":
                      System.out.println("Type a filename where you'd like to save the flashcards: ");
                      String outFilePath = scanner.next();
                      displayer.saveFlashcards(outFilePath);
                  case "quiz":
                      displayer.displayFlashcards();
                  case "exit":
                      System.out.println("You have exited the program.");
                      System.exit(1);
                }
            }
        }
    }
}
