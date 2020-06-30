import java.io.*;
import java.util.*;

public class WordCounter{
    private WordCountMap map;
    private Map<String, String> stopWordsMap = new HashMap<>();

    public WordCounter(){
        map = new WordCountMap();
    }
    public WordCountMap getMap(){
        return map;
    }

    public Map<String, String> getStopWordsMap() {
        return stopWordsMap;
    }

    /*
    a method to load all the stopwords to a hashmap
    */
    public void loadStopWords(String filePath) {
        File file = new File(filePath);
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }

        while (scan.hasNextLine()) {
            String word = scan.nextLine();
            stopWordsMap.put(word, word);
        }
    }

    /*
    a boolean method that returns true if a particular word is a stopword.
    returns false if the word is not a stopword
    */
    public boolean isStopWord(String word) {
        if (this.getStopWordsMap().containsKey(word)) {
            return true;
        } else {
            return false;
        }
    }

    /*
    a method that formats the word so that the word fits the following conditions:
     - the word starts with a letter or a digit
     - the word ends with a letter or a digit
     - the word does not have any spaces between letters
     - all the letters in the word are lowercase
    */
    public static String formatWord(String word){
        //the case that the length of the word is 1 so that it does not throw an indexoutofboundsexception
        if (word.length() == 1){
            if(Character.isLetterOrDigit(word.charAt(0))){
                return word.toLowerCase();
            }
            else {
                return "";
            }
        }
        //checks if either the first or last character in word is a letter or not
        if (Character.isLetterOrDigit(word.charAt(0)) || Character.isLetterOrDigit(word.charAt(word.length() - 1))){
            // if both are letters, simply turn word into lowercase and return it
            if (Character.isLetterOrDigit(word.charAt(0)) && Character.isLetterOrDigit(word.charAt(word.length() - 1))){
                return word.toLowerCase();
            }
            // means that punctuation must be stripped from end
            else if (Character.isLetterOrDigit(word.charAt(0))){
                while(!Character.isLetterOrDigit(word.charAt(word.length() - 1))){
                    word = word.substring(0, word.length() - 1);
                }
                return word.toLowerCase();
            }
            // means that punctuation must be stripped from beginning
            else if (Character.isLetterOrDigit(word.charAt(word.length() - 1))){
                while(!Character.isLetterOrDigit(word.charAt(0))){
                    word = word.substring(1, word.length());
                }
                return word.toLowerCase();
            }
        }
        // means neither first not last character is a letter, which means punctuation must be stripped from both front and back
        while(!Character.isLetterOrDigit(word.charAt(word.length() - 1))){
            if (word.length() == 1){
                return "";
            }
            word = word.substring(0, word.length() - 1);
        }
        while(!Character.isLetterOrDigit(word.charAt(0))){
            if (word.length() == 1){
                return "";
            }
            word = word.substring(1, word.length());
        }
        return word.toLowerCase();
    }

    /*
    Loads all word from a file into a WordCountMap
    */
    public void loadWords(String path){
        // load words in file, excluding stop words and formatting them properly
        // add words to a wordCountMap, use getWordCountsByCount() to get a list of sorted word count objects
        // display them line by line
        File file = new File(path);
        Scanner scan = null;
            try{
                scan = new Scanner(file);
            }
            catch (FileNotFoundException e) {
                System.out.println("File is not found");
                System.exit(1);
            }
            // while file still has lines to scan
            while (scan.hasNext()){
                String word = scan.next();
                // for each word, format it, check if it's a stop word, then add to wordCountMap
                    word = formatWord(word);
                    //System.out.println("Word: " + word);
                    if (this.isStopWord(word)){
                        continue;
                    }
                    else{
                        this.getMap().incrementCount(word);
                    }
            }
    }
    //a method to display all the words and their frequency in a sorted decreasing order
    public void displayWordsByCount(){
        List<WordCount> wordList = this.getMap().getWordCountsByCount();
        for (int i = 0; i < wordList.size(); i++){
            System.out.println( wordList.get(i).getWord() + ": " + wordList.get(i).getCount());
        }
    }
    public static void main(String[] args){
        WordCounter counter = new WordCounter();
        counter.loadStopWords("StopWords.txt");
        //the case where no arguments are passed into the commandline
        //asks the user to enter the right commandline arguments in the correct format
        if (args.length == 0){
            System.out.println("Please enter a command line argument.");
            System.exit(1);
        }
        //the case where 1 argument is passed
        //prints all the words and their count in sorted decreasing order
        else if (args.length == 1){
            counter.loadWords(args[0]);
            counter.displayWordsByCount();
        }
        //the case where 3 arguments are passed
        //in which case the wordcloudfile is created
        else if (args.length == 3){
            String textFilePath = args[0];
            int numberOfWordsToInclude = Integer.parseInt(args[1]);
            String outFileName = args[2];

            try{
                File file = new File(outFileName);
            }
            catch(Exception e){
                System.out.println("An error occurred!");
            }
            counter.loadWords(args[0]);
            List<WordCount> list = counter.getMap().getWordCountsByCount();
            list = list.subList(0, numberOfWordsToInclude);
            try{
                FileWriter myWriter = new FileWriter(outFileName);
                myWriter.write(WordCloudMaker.getWordCloudHTML("Word Cloud", list));
                myWriter.close();
            }
            catch(IOException e){
                System.out.println("An error occurred.");
            }
        }
        else{
            System.out.println("Please enter either 1 or 4 command line arguments in the correct format.");
        }
    }
}
