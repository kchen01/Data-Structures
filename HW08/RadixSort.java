import java.util.*;
import java.io.*;

public class RadixSort{

  //a constant string that contains all the possible char in a string, used a reference to put words into appropriate queues
  public static final String ALPHABET = "*abcdefghijklmnopqrstuvwxyz";

  //static method that finds the longest length of the strings in words
  public static int findLongestLength(List<String> list){
      int longestLength = 0;
      for (String word: list){
            if (word.length() > longestLength){
            longestLength = word.length();
            }
      }
    return longestLength;
  }
  //static method that asterisks to the end of all words to make them all the same length as the longest word
  public static List<String> addAsterisks(List<String> list, int longestLength){
      String temp = "";
      for (int i = 0; i < list.size(); i++) {
          while (list.get(i).length() != longestLength) {
              temp = list.get(i) + "*";
                list.set(i, temp);
          }
      }
      return list;
  }
  /*
    method that sorts the words in the input list by their first letter and puts them into the appropriate queues. Then, it polls each queue, puts into
    a list, uses radixSort() to sort each of those lists, and combins all of htem back into final sorted list.
  */
  public static List<String> msdRadixSort(List<String> words){
    //create a List to hold queues
    List<Queue<String>> queues = new ArrayList<Queue<String>>();
    //creating 27 queues and adding them to list of queues
    for (int i = 0; i < 27; ++i) {
        queues.add(new LinkedList<String>());
    }
    //finding the longest length of the strings in words and adding asterisks to all of them.
    int longestLength = findLongestLength(words);
    words = addAsterisks(words, longestLength);

    //gets first character in each word and uses it to place each word in appropriate queue
    for (int j = 0; j < words.size(); j++) {
        int num = (ALPHABET.indexOf(words.get(j).charAt(0)));
        queues.get(num).add(words.get(j));
    }

    List<String> finalList = new ArrayList<String>();
    List<String> temp = null;

    //checks if each queue if empty. If not, poll it and add to a temporary list that is sent to RadixSort to get sorted and combined into a final sorted list.
    for (Queue each: queues) {
        if (each.isEmpty()){
          continue;
        }
        temp = new ArrayList<String>();
        while (!each.isEmpty()) {
            temp.add(each.poll().toString());
        }
        temp = radixSort(temp);
        for (String each2: temp) {
            finalList.add(each2);
        }
    }
    return finalList;
  }
  /*
    method that sorts the words in the input list. Starting from the last letter, it puts words into queues according to that letter, polls them out into a temporary list,
    then repeats until after the words are sorted by the first letter, after which the list is completely sorted.
  */
  public static List<String> radixSort(List<String> words){
      List<Queue<String>> queues = new ArrayList<Queue<String>>();

      //creating a queue of 27 LinkedList
      for (int i = 0; i < 27; ++i) {
        queues.add(new LinkedList<String>());
      }
      //finding the longest length of the strings in words and adding asterisks to all of them.
      int longestLength = findLongestLength(words);
      words = addAsterisks(words, longestLength);
      List<String> temp = null;
      // Loop where the sorting happens. Starting from the last letter, words are sorted into the appropriate queues, polled into a temporary list. This is
      // repeated letter by letter until after the first letter is used, after which the list is completely sorted.
      for (int i = longestLength; i > 0; i--) {
          for (int j = 0; j < words.size(); j++) {
            if (i == longestLength) {
              int num = (ALPHABET.indexOf(words.get(j).charAt(i-1)));
              queues.get(num).add(words.get(j));
            }
            else {
              int num = (ALPHABET.indexOf(temp.get(j).charAt(i-1)));
              queues.get(num).add(temp.get(j));
            }
          }
          //polls each queue and puts into a temporary list.
          temp = new ArrayList<String>();
          for (Queue each: queues) {
              while (!each.isEmpty()) {
                  temp.add(each.poll().toString());
              }
          }
      }
      //removes asterisks from words after being sorted
      int indexOf = 0;
      String newString = "";
      for (int w = 0; w < temp.size(); w++) {
          indexOf = temp.get(w).indexOf("*");
          if (indexOf != -1) {
                newString = temp.get(w).substring(0,indexOf);
                temp.set(w, newString);
          }
      }
      return temp;
    }
    public static void main(String[] args){
        //gets command line argument
        String fileName = args[0];
        List<String> wordsList = new ArrayList<String>();
        List<String> testList = new ArrayList<String>();
        Scanner scanner = null;

        //loads words from file, turns them into lower case, and adds them to the test list
        try {
           scanner = new Scanner(new File(fileName));
           while (scanner.hasNext()){
               String word = scanner.nextLine();
               wordsList.add(word.toLowerCase());
         }
        }
        catch(FileNotFoundException e){
            System.out.println("No file was found.");
        }
        testList = wordsList;
        double average = 0;
        int total = 0;
        int time = 0;
        for( int i = 0; i < 100; i++){
            Collections.shuffle(testList);
            //testList = wordsList.subList(0, 100000);
            //testList = Collections.reverse(wordsList);
            long startTime = System.currentTimeMillis();
            //radixSort(testList);
            //msdRadixSort(testList);
            long endTime = System.currentTimeMillis();
            time = (int)(endTime - startTime);
            total = total + time;
            System.out.println("time to sort (ms): " + time);
        }
        average = (double) total / 100;
        System.out.println("Average: " + average);
    }
}
