import java.util.*;
import java.time.*;

public class FlashcardPriorityQueue implements PriorityQueue<Flashcard>{
    private Flashcard[] flashcards; // array that holds the cards
    private int lastIndex; // index of the last card added to the heap
    public static final int DEFAULT_CAPACITY = 20; //the default capacity of the array when making a new priorty queue
    /*
    This method resizes the array by creating a new array that holds double the current capacity and copies all the items over.
    */
    public void resize(){
        Flashcard[] tempArray = new Flashcard[((flashcards.length - 1) * 2) + 1];
        for (int i = 0; i < flashcards.length; i++) {
            tempArray[i] = flashcards[i];
        }
        flashcards = tempArray;
    }
    /*
    This method returns the array of Flashcards.
    */
    public Flashcard[] getFlashcards(){
        return flashcards;
    }
    /**
    * Creates an empty priority queue.
    */
    public FlashcardPriorityQueue(){
        flashcards = new Flashcard[DEFAULT_CAPACITY + 1];
        lastIndex = 1;
    }
    public void add(Flashcard card){
      int curIndex = 0;
      // if full, resize
      if (lastIndex == flashcards.length - 1){
          resize();
      }
      // if empty, add the card
      if (isEmpty()){
          flashcards[lastIndex] = card; // set card to the lastIndex, which is 1
      }
      // otherwise, heap is not empty
      else {
          lastIndex += 1; // increment lastIndex before adding
          flashcards[lastIndex] = card; // add new card to correct spot on heap
          // check newly added card with parent and swap if necessary.
          curIndex = lastIndex;
          while (checkParent(curIndex) == false) {
              swapParent(curIndex);
              curIndex = (curIndex + 1) / 3;
              if (curIndex <= 1) {
                  break;
              }
          }
      }
    }
    /*
    This method returns true if parent and child are in correct position, false if not.
    */
    public boolean checkParent(int curIndex) {
        if (flashcards[curIndex].compareTo(flashcards[(curIndex + 1) / 3]) < 0){
            return false; // means that the current node and its parent must be swapped
        }
        return true;
    }
    /*
    This method swaps the card at curIndex and its parent.
    */
    public void swapParent(int curIndex){
        Flashcard temp = flashcards[(curIndex + 1) / 3]; // stores value of parent
        flashcards[(curIndex + 1) / 3] = flashcards[curIndex]; // puts child card to the parent's index
        flashcards[curIndex] = temp; // places parent card to the child's index
    }
    /*
    This method removes the highest priority card in the heap
    */
    public Flashcard poll(){
        if (isEmpty()){
            throw new NoSuchElementException("The FlashcardPriorityQueue is empty.");
        }
        Flashcard pollCard = flashcards[1]; // stored value being polled
        flashcards[1] = flashcards[lastIndex]; // set root to the last item
        flashcards[lastIndex] = null; // set last item to null
        lastIndex = lastIndex - 1; // decrement lastIndex

        int curIndex = 1; // set curIndex to root
        // while curIndex has children, swap if necessary
        int swapChildIndex = getIndexOfSmallestChild(curIndex);
        while (hasChild(curIndex) && checkChild(curIndex)) {
            bubbleUpSmallestChild(curIndex, swapChildIndex);
            curIndex = swapChildIndex;
            swapChildIndex = getIndexOfSmallestChild(curIndex);
        }
        return pollCard; // return the card that was polled
    }
    /*
    Returns true if parent needs to be swapped with one of its children, false if not.
    */
    public boolean checkChild(int curIndex){
        if (flashcards[curIndex].compareTo(flashcards[getIndexOfSmallestChild(curIndex)]) > 0){
            return true;
        }
        return false;
    /*
    This method swaps a parent card with its smallest child
    */
    }
    public void bubbleUpSmallestChild(int parentIndex, int minChildIndex) {
        Flashcard temp = flashcards[parentIndex];
        flashcards[parentIndex] = flashcards[minChildIndex];
        flashcards[minChildIndex] = temp;
    /*
    This method checks if the card at curIndex has children.
    */
    }
    public boolean hasChild(int curIndex) {
        if (flashcards[(3 * curIndex) - 1] != null) {
            return true;
        }
        else {
            return false;
        }
    }
    /*
    This method gets the index of the smallest child of the card at curIndex
    */
    public int getIndexOfSmallestChild(int curIndex){
        int smallestChildIndex = (3 * curIndex) - 1; // initially set left child to the smallestChildIndex
        // if middle child is null, return the left child
        if (flashcards[smallestChildIndex + 1] == null) {
            return smallestChildIndex;
        }
        // if right child is null
        else if (flashcards[smallestChildIndex + 2] == null) {
            if (flashcards[smallestChildIndex].compareTo(flashcards[3 * curIndex]) < 0){
                smallestChildIndex = 3 * curIndex; // return middle child
            }
        }
        // if all three children exist, then compare all three
        else {
            if (flashcards[smallestChildIndex].compareTo(flashcards[3 * curIndex]) > 0){
                smallestChildIndex = 3 * curIndex;
            }
            if (flashcards[smallestChildIndex].compareTo(flashcards[(3 * curIndex) + 1]) > 0){
                smallestChildIndex = (3 * curIndex) + 1;
            }
        }
        return smallestChildIndex;
    /*
    This method returns the highest priority card without removing it
    */
    }
    public Flashcard peek(){
        return flashcards[1];
    }
    /*
    This method returns true if the heap is empty, false if not.
    */
    public boolean isEmpty(){
        if (flashcards[1] == null){
            return true;
        }
        return false;
    }
    /*
    Clears heap by creating a new array
    */
    public void clear(){
        flashcards = new Flashcard[DEFAULT_CAPACITY + 1];
        lastIndex = 1;
    }
    public static void main(String[] args){
      FlashcardPriorityQueue testPriorityQueue = new FlashcardPriorityQueue();

      //System.out.println("test.isEmpty() should return true here: " + test.isEmpty());
      System.out.println("should print true! : " + testPriorityQueue.isEmpty());

      Flashcard test1 = new Flashcard("2000-11-12T13:03", "1st", "Amigo");
      Flashcard test2 = new Flashcard("2001-12-12T13:03", "2nd", "bye");
      Flashcard test3 = new Flashcard("2002-12-12T13:03", "3rd", "bye");
      Flashcard test4 = new Flashcard("2004-12-12T13:03", "4th", "uyg3fr");
      Flashcard test5 = new Flashcard("2005-12-12T13:03", "5th", "llwf");
      Flashcard test6 = new Flashcard("2006-12-12T13:03", "6th", "alo");
      Flashcard test7 = new Flashcard("2007-12-12T13:03", "7th", "igylf");
      Flashcard test8 = new Flashcard("2008-12-12T13:03", "8th", "w0Y8HO");
      Flashcard test9 = new Flashcard("2009-12-12T13:03", "9th", "we;gfiu");
      Flashcard test10 = new Flashcard("2010-12-12T13:03", "10th", "igweiou");
      Flashcard test11 = new Flashcard("2011-12-12T13:03", "11th", "cars");
      Flashcard test12 = new Flashcard("2012-12-12T13:03", "12th", "jeep");
      Flashcard test13 = new Flashcard("2013-12-12T13:03", "13th", "popppp");
      Flashcard test14 = new Flashcard("2014-12-12T13:03", "14th", "salamander");
      Flashcard test15 = new Flashcard("2015-12-12T13:03", "15th", "isnake");
      Flashcard test16 = new Flashcard("2016-12-12T13:03", "16th", "ierhe");
      Flashcard test17 = new Flashcard("2017-12-12T13:03", "17th", "igywes");
      Flashcard test18 = new Flashcard("2018-12-12T13:03", "18th", "fuowbkjf");
      Flashcard test19 = new Flashcard("2019-12-12T13:03", "19th", "wlgiuw");
      Flashcard test20 = new Flashcard("2020-12-12T13:03", "20th", "8774y");
      Flashcard test21 = new Flashcard("2021-12-12T13:03", "21st", "aifug");
      Flashcard test22 = new Flashcard("2022-12-12T13:03", "22nd", "woefghu");
      Flashcard test23 = new Flashcard("2023-12-12T13:03", "23rd", "giluerg");


      System.out.println("the initial array length should be 21:  " + testPriorityQueue.getFlashcards().length);
      testPriorityQueue.add(test23);

      System.out.println("should print false! : " + testPriorityQueue.isEmpty());

      testPriorityQueue.add(test22);
      testPriorityQueue.add(test21);
      testPriorityQueue.add(test20);
      testPriorityQueue.add(test19);
      testPriorityQueue.add(test18);
      testPriorityQueue.add(test17);
      testPriorityQueue.add(test16);
      testPriorityQueue.add(test15);
      testPriorityQueue.add(test14);
      testPriorityQueue.add(test13);
      testPriorityQueue.add(test12);
      testPriorityQueue.add(test11);
      testPriorityQueue.add(test10);
      testPriorityQueue.add(test9);
      testPriorityQueue.add(test8);
      testPriorityQueue.add(test7);
      testPriorityQueue.add(test6);
      testPriorityQueue.add(test5);
      testPriorityQueue.add(test4);
      testPriorityQueue.add(test3);
      testPriorityQueue.add(test2);
      testPriorityQueue.add(test1);
      System.out.println("the array length now should be 41:  " + testPriorityQueue.getFlashcards().length);
      System.out.println("the peek value should be 1:  " + testPriorityQueue.peek().getFrontText());


      Flashcard[] flashcards = testPriorityQueue.getFlashcards();
      System.out.println("the following print series should come out in order of:  1,3,2,12,10,7,4,15,17,16,21,14,13,23,18,11,20,9,8,19,6,5,22");
      for (int i = 1; i < 24; i++) {
          System.out.println(flashcards[i].getFrontText());
      }
      System.out.println("-----------------------------");

      Flashcard a = testPriorityQueue.poll();
      System.out.println("flashcard a should be 1 and 'Amigo':  " + a.getFrontText() + "   " + a.getBackText() );
      System.out.println("the following print series should come out in order of:  2,3,15,12,10,7,4,22,17,16,21,14,13,23,18,11,20,9,8,19,6,5");
      flashcards = testPriorityQueue.getFlashcards();
      for (int i = 1; i < 23; i++) {
          System.out.println(flashcards[i].getFrontText());
      }

      System.out.println("-----------------------------");
      Flashcard b = testPriorityQueue.poll();
      System.out.println("the following print series should come out in order of:  3,4,15,12,10,7,5,22,17,16,21,14,13,23,18,11,20,9,8,19,6");
      System.out.println("flashcard b should be 2:  " + b.getFrontText());
      flashcards = testPriorityQueue.getFlashcards();
      for (int i = 1; i < 22; i++) {
          System.out.println(flashcards[i].getFrontText());
      }

      System.out.println("-----------------------------");
      Flashcard c = testPriorityQueue.poll();
      System.out.println("flashcard c should be 3:  " + c.getFrontText());
      flashcards = testPriorityQueue.getFlashcards();
      for (int i = 1; i < 21; i++) {
          System.out.println(flashcards[i].getFrontText());
      }

      System.out.println("-----------------------------");
      Flashcard d = testPriorityQueue.poll();
      System.out.println("flashcard d should be 4:  " + d.getFrontText());
      flashcards = testPriorityQueue.getFlashcards();
      for (int i = 1; i < 20; i++) {
          System.out.println(flashcards[i].getFrontText());
      }

      System.out.println("-----------------------------");
      Flashcard f = testPriorityQueue.poll();
      System.out.println("flashcard e should be 5:  " + f.getFrontText());
      flashcards = testPriorityQueue.getFlashcards();
      for (int i = 1; i < 19; i++) {
          System.out.println(flashcards[i].getFrontText());
      }
      System.out.println("-----------------------------");


      testPriorityQueue.clear();
      System.out.println("should print true! : " + testPriorityQueue.isEmpty());

      try {
      Flashcard emptyPoll = testPriorityQueue.poll();
      System.out.println("Shouldn't print this");
      } catch(NoSuchElementException e) {
      System.out.println("poll() successfully threw a NoSuchElementException.");
      }

      try {
      Flashcard emptyPeek = testPriorityQueue.poll();
      System.out.println("Shouldn't print this");
      } catch(NoSuchElementException e) {
      System.out.println("poll() successfully threw a NoSuchElementException.");
      }

    }
}
