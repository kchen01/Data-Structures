import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * In class lab for learning about hash code functions and collisions.
 * @author arafferty
 * @author YOUR NAME HERE
 */
public class HashCodeCalculations {

    /**
     * Always returns 0.
     */
    public static int hashCode0(String s) {
        return 0;
    }

    /**
     * if empty -> 0 , otherwise, return ASCII value of the 1st char.
     */
    public static int hashCode1(String s) {
        if(s.isEmpty()) {
            return 0;
        } else {
            return (int) s.charAt(0);
        }
    }

    /**
     * return ASCII values of total char ASCII
     */
    public static int hashCode2(String s) {
        int hashCode = 0;
        for(int i = 0; i < s.length(); i++) {
            hashCode += (int) s.charAt(i);
        }
        return hashCode;
    }

    /**
     * // 85647 ((8*129 + 5)*129 + 6)*129+ -> number is huge -> size array is huge -> compress?
     */
    public static int hashCode3(String s) {
        int hashCode = 0;
        for(int i = 0; i < s.length(); i++) {
            hashCode = 129*hashCode + (int) s.charAt(i);
        }
        return hashCode;
    }

    /**
     * Implement this function so it works the way we talked about in class.
     * Compression function that takes a hash code (positive or negative) and
     * the number of buckets we have to use in our hash table, and compresses
     * the hash code into the range [0, numberOfBuckets).
     */
    public static int compressToSize(int hashCode, int numberOfBuckets) {
        int returnValue = hashCode % numberOfBuckets;
        if (returnValue < 0) {
            returnValue += numberOfBuckets;
        }
        return returnValue;
    }

    /**
     * Counts the number of buckets that have no words stored at them - i.e.,
     * they have value 0 - and calculates what proportion of the total buckets
     * that is.
     */
    public static double proportionOfBucketsWithNoWords(int[] buckets) {
        int emptyBucketCount = 0;
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i] == 0) {
                emptyBucketCount++;
            }
        }
        return emptyBucketCount*1.0/buckets.length;
    }

    /**
     * Returns the maximum value in a single bucket
     */
    public static int getMaxBucketValue(int[] buckets) {
        int max = -1;//Safe starting value since all buckets[i] should be >= 0
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i] > max) {
                max = buckets[i];
            }
        }
        return max;
    }

    /**
     * Returns the average number of words in each non-empty bucket
     */
    public static double getAverageInNonEmptyBuckets(int[] buckets) {
        int totalCount = 0;
        int totalNonEmpty = 0;
        for(int i = 0; i < buckets.length; i++) {
            totalCount += buckets[i];
            if(buckets[i] != 0) {
                totalNonEmpty++;
            }
        }
        return totalCount*1.0/totalNonEmpty;
    }

    /**
     * Implement this method so that it calculates how many words would be placed
     * in each bucket in the array.
     * Each individual word should be counted only once (i.e., if "the" occurs
     * 501 times in the file, you should only hash it once, rather than thinking
     * of it as causing 500 collisions).
     * @param numBuckets number of spots to include in the array
     * @param file file to read from
     * @param hashCodeFunctionToUse which of the hash functions to use; see lab
     *                              description for more details
     * @return an array that indicates how many different words are place in index 0, 1, etc.
     */
    public static int[] collisionCounter(int numBuckets, String file, int hashCodeFunctionToUse) {
        //Initialize the variables you'll need to count collisions (an array, a set)
        int[] buckets = new int[numBuckets];
        HashSet<String> set = new HashSet<String>();

        try {
            Scanner scanner =  new Scanner(new File(file));
            String line = "";
            String word = "";
            while (scanner.hasNextLine()){
                line = scanner.nextLine();
                try {
                    Scanner scanner2 = new Scanner(line).useDelimiter(" ");
                    while (scanner2.hasNext()) {
                      word = scanner2.next();
                      //if the word is not contained, calculate hash code, increment correct bucket, and add to the set
                      if(!set.contains(word)) {
                          if (hashCodeFunctionToUse < -1 || hashCodeFunctionToUse >= 3){
                              buckets[compressToSize(hashCode3(word), numBuckets)] = buckets[compressToSize(hashCode3(word), numBuckets)] + 1;
                          }
                          else if (hashCodeFunctionToUse == -1){
                              buckets[compressToSize(word.hashCode(), numBuckets)] = buckets[compressToSize(word.hashCode(), numBuckets)] + 1;
                          }
                          else if (hashCodeFunctionToUse == 0){
                              buckets[compressToSize(hashCode0(word), numBuckets)] = buckets[compressToSize(hashCode0(word), numBuckets)] + 1;
                          }
                          else if (hashCodeFunctionToUse == 1){
                              buckets[compressToSize(hashCode1(word), numBuckets)] = buckets[compressToSize(hashCode1(word), numBuckets)] + 1;
                          }
                          else if (hashCodeFunctionToUse == 2){
                              buckets[compressToSize(hashCode2(word), numBuckets)] = buckets[compressToSize(hashCode2(word), numBuckets)] + 1;
                          }
                          set.add(word);
                      }
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return buckets;
    }

    public static void main(String[] args) {
        int[] testBuckets = collisionCounter(200000, "HoundOfTheBaskervilles.txt", 3);
        //System.out.println("Max bucket value: " + getMaxBucketValue(testBuckets));
        System.out.println("# empty buckets: " + proportionOfBucketsWithNoWords(testBuckets) * 200000);
        System.out.println("Average bucket value: " + getAverageInNonEmptyBuckets(testBuckets));


    }


}
