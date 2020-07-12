import java.util.*;

public class WordCountMap{
    private Node root;

    private class Node{
        private int count;
        private String letter;
        private Node prevNode;
        private Map<String, Node> nextNodeMap;

        private Node(){
            letter = "";
            count = 0;
            prevNode = null;
            nextNodeMap = new HashMap<String, Node>();
        }
        private Node(String newLetter, Node newPrevNode) {
            letter = newLetter;
            prevNode = newPrevNode;
            nextNodeMap = new HashMap<String, Node>();
            count = 0;
        }
        private void addToMap(String letter, Node nextNode){
            nextNodeMap.put(letter, nextNode);
        }
        private void removeFromMap(String letter){
            nextNodeMap.remove(letter);
        }
        private void addCount(){
            count += 1;
        }
        private void lowerCount(){
            count -= 1;
        }
        private int getCount(){
            return count;
        }
        private String getLetter(){
            return letter;
        }
        private Map<String, Node> getNextNodeMap(){
            return nextNodeMap;
        }
        private Node getPrevNode(){
            return prevNode;
        }
    }
    /**
    * Constructs an empty WordCountMap.
    */
    public WordCountMap(){
        root = new Node();
    }
    /**
    * Adds 1 to the existing count for word, or adds word to the WordCountMap
    * with a count of 1 if it was not already present.
    * Implementation must be recursive, not iterative.
    */
    public void incrementCount(String word){
        incrementCount(word, root);
    }
    //helper method
    private void incrementCount(String word, Node curNode) {
        //if the substring is now empty, increment the count of the current Node
        if (word.length() == 0) {
            curNode.addCount();
        }
        else {
            Node nextNode = null;
            Map<String, Node> curNodeMap = curNode.getNextNodeMap();
            // checks whether if the letter is in the current Node's map. If yes, then set nextNode to the next node, and call function again
            if (curNodeMap.containsKey(Character.toString(word.charAt(0)))){
                nextNode = curNodeMap.get(Character.toString(word.charAt(0)));
            //the case where the letter does not exist as a child of the start Node. Create new node, add it to curNodeMap, and call function again
            } else {
                nextNode = new Node(Character.toString(word.charAt(0)), curNode);
                curNodeMap.put(Character.toString(word.charAt(0)), nextNode);
            }
            incrementCount(word.substring(1), nextNode);
        }
    }
    /**
    * Remove 1 to the existing count for word. If word is not present, does
    * nothing. If word is present and this decreases its count to 0, removes
    * any nodes in the tree that are no longer necessary to represent the
    * remaining words.
    */
    public void decrementCount(String word){
        // if there is only the root, throw an exception and a message
        if (getNodeCount() == 1){
            throw new NullPointerException("Map is empty.");
        }
        // if the word is not contained in the tree, throw an exception and a message
        else if (!contains(word)){
            throw new NullPointerException("Word is not in this map.");
        }
        // otherwise, call the helper method to actually decrement the count
        else {
            decrementCount(word, root);
        }
    }

    public void decrementCount(String word, Node curNode){
        //correct node has been reached
        if (word.length() == 0){
            if (curNode.getLetter().equals("")) {
                return;
            }
            //if count is 0, remove node if it has no children or leave method if it does
            if (curNode.getCount() == 0){
                //if curNode doesn't have any children, delete curNode and call decrementCount on the parent node of curNode
                if (curNode.getNextNodeMap().isEmpty()) {
                    Node parentNode = curNode.getPrevNode();
                    parentNode.getNextNodeMap().remove(curNode.getLetter());
                    decrementCount(word, parentNode);
                }
                //if it does have children, then leave method
                return;
            }
            //when count is greater than 0, leave method
            return;
        }
        //if correct node has not been reached, keep moving down tree
        Node nextNode = null;
        Map<String, Node> curNodeMap = curNode.getNextNodeMap();
        nextNode = curNodeMap.get(Character.toString(word.charAt(0)));
        //if the passed in String has length 1, then decrement count of the next node
        if (word.length() == 1) {
            nextNode.lowerCount();
        }
        //call decrementCount again with the first letter omitted and the next node
        decrementCount(word.substring(1), nextNode);
    }
    /**
    * Returns true if word is stored in this WordCountMap with
    * a count greater than 0, and false otherwise.
    * Implementation must be recursive, not iterative.
    */
    public boolean contains(String word){
        if (root.getNextNodeMap().isEmpty()) {
            return false;
        }
        return contains(word, root);
    }
    //helper method
    private boolean contains(String word, Node curNode) {
      //if the substring is now empty, check if the count of the node is more than one
      if (word.length() == 0) {
          if (curNode.getCount() > 0) {
              return true;
          } else {
              return false;
          }
      }
      else {
          //if correct node has not been reached, keep moving down tree
          Node nextNode = null;
          Map<String, Node> curNodeMap = curNode.getNextNodeMap();
          nextNode = curNodeMap.get(Character.toString(word.charAt(0)));
          // if the next node is null, return false
          if (nextNode == null){
              return false;
          }
          //otherwise call contains again with the first letter omitted and the next node
          return contains(word.substring(1), nextNode);
      }
    }

    /**
    * Returns the count of word, or -1 if word is not in the WordCountMap.
    * Implementation must be recursive, not iterative.
    */
    public int getCount(String word){
        //if the word is not contained, then automatically return 0.
        if (!this.contains(word)){
            return 0;
        }
        //otherwise call helper method
        return getCount(word, root);
    }
    //helper method
    private int getCount(String word, Node curNode) {
        //if the substring is now empty, return the count of the curNode.
        if (word.length() == 0) {
            return curNode.getCount();
        }
        else {
            Node nextNode = null;
            Map<String, Node> curNodeMap = curNode.getNextNodeMap();
            nextNode = curNodeMap.get(Character.toString(word.charAt(0)));
            return getCount(word.substring(1), nextNode);
        }
    }
    /**
    * Returns a list of WordCount objects, one per word stored in this
    * WordCountMap, sorted in decreasing order by count.
    */
    public List<WordCount> getWordCountsByCount(){
        List<WordCount> list = new ArrayList<WordCount>();
        // if root has no children, then return empty list
        if (root.getNextNodeMap().isEmpty()) {
            return list;
        }
        return getWordCountsByCount(root, list);
    }

    private List<WordCount> getWordCountsByCount(Node curNode, List<WordCount> list){
        // recursively go to every node in the tree
        Collection<Node> childNodes = curNode.getNextNodeMap().values();
        Iterator<Node> iterator = childNodes.iterator();

        if (curNode.getCount() > 0){
            WordCount wordCount = new WordCount(buildWord(curNode), curNode.getCount());
            list.add(wordCount);
        }
        while(iterator.hasNext()){
            getWordCountsByCount(iterator.next(), list);
        }

        InsertionSort(list);
        return list;
    }
    public static List<WordCount> InsertionSort(List<WordCount> list){
        // each iteration of for loop puts one WordCount in right spot
        for (int i = 1; i < list.size(); i ++)
        {
            // item is WordCount that is being put in its correct place
            WordCount item = list.get(i);
            int prevIndex = i - 1;
            // navigates from back of list to front until the right spot for item is found
            while (prevIndex >= 0 && list.get(prevIndex).getCount() < item.getCount())
            {
                list.set(prevIndex + 1, list.get(prevIndex));
                prevIndex = prevIndex - 1;
            }
            // sets item to proper spot after finding correct spot
            list.set(prevIndex + 1, item);
        }
        return list;
    }
    public String buildWord (Node curNode) {
        Stack<String> wordStack = new Stack<String>();
        wordStack.push(curNode.getLetter());
        while (curNode != root) {
            //System.out.println("the letter ar curNode is:  " + curNode.getLetter());
            curNode = curNode.prevNode;
            wordStack.push(curNode.getLetter());
        }
        String word = "";
        while (!wordStack.empty()) {
            //System.out.println("the letter at the top of the stack is:  "  + wordStack.peek());
            word = word + wordStack.pop();
        }
        //System.out.println("the built word is: " + word);
        return word;
    }

    /**
    * Returns a count of the total number of nodes in the tree.
    * A tree with only a root is a tree with one node; it is an acceptable
    * implementation to have a tree that repre0.
    sents no words have either
    * 1 node (the root) or 0 nodes.
    * Implementation must be recursive, not iterative.
    */

    public int getNodeCount(){
        // if root has no children, return 1
        if (root.getNextNodeMap().isEmpty()){
            return 1;
        }
        // otherwise call helper method to find the node count
        return 1 + getNodeCount(root);
    }
    private int getNodeCount(Node curNode) {
        int count = 0;
        // base case: if curNode has no children, then return 0
        if (curNode.getNextNodeMap().isEmpty()) {
            return 0;
        }
        else {
            // recursively go to every node in the tree, incrementing by one each time
            Collection<Node> childNodes = curNode.getNextNodeMap().values();
            Iterator<Node> iterator = childNodes.iterator();
            while(iterator.hasNext()){
                count = count + 1 + getNodeCount(iterator.next());
            }
        }
        return count;
    }
    /*
    Clears the WordCountMap.
    */
    public void clear(){
        // sets root to a new Node, thus losing all the rest of the nodes
        root = new Node();
    }
    public static void main(String[] args){
        WordCountMap wordCountMap = new WordCountMap();

        System.out.println("TESTS FOR incrementCount(): ");
        //test if testIncrementCount() properly adds to empty list
        wordCountMap.incrementCount("test");
        System.out.println("The count for 'test' should be 1. getCount() returns : " + wordCountMap.getCount("test"));

        //test if clear() properly clears
        wordCountMap.clear();
        System.out.println("The count for 'test' should be 0. getCount() returns : " + wordCountMap.getCount("test"));

        //test if testIncrementCount() properly adds multiple counts to same word
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        System.out.println("The count for 'test' should be 3. getCount() returns : " + wordCountMap.getCount("test"));

        //tests if "test" count remains correct after adding the following words
        wordCountMap.incrementCount("tes");
        wordCountMap.incrementCount("tests");
        wordCountMap.incrementCount("t");
        wordCountMap.incrementCount("ps");
        wordCountMap.incrementCount("tps");
        wordCountMap.incrementCount("tps");
        System.out.println("The count for 'test' should still be 3. getCount() returns : " + wordCountMap.getCount("test"));
        System.out.println("The count for 'tests' should be 1. getCount() returns : " + wordCountMap.getCount("tests"));
        System.out.println("The count for 'tes' should be 1. getCount() returns : " + wordCountMap.getCount("tes"));
        System.out.println("The count for 't' should be 1. getCount() returns : " + wordCountMap.getCount("t"));
        System.out.println("The count for 'ps' should be 1. getCount() returns : " + wordCountMap.getCount("ps"));
        System.out.println("The count for 'tps' should be 2. getCount() returns : " + wordCountMap.getCount("tps"));
        wordCountMap.clear();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("TESTS FOR decrementCount(): ");
        //test if decrementCount() properly throws an exception for decrementing from empty list.
        try{
            wordCountMap.decrementCount("test");
            System.out.println("decrementCount() failed to throw an exception here.");
        }
        catch(NullPointerException e){
            System.out.println("decrementCount() properly threw an exception here.");
        }

        //test if decrementCount() properly decrements and removes nodes
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");

        System.out.println("Before decrementing, 'test' should have a count of 5: " + wordCountMap.getCount("test"));
        System.out.println("Before decrementing, getNodeCount() should return 5: " + wordCountMap.getNodeCount());
        wordCountMap.decrementCount("test");
        wordCountMap.decrementCount("test");
        wordCountMap.decrementCount("test");
        wordCountMap.decrementCount("test");
        wordCountMap.decrementCount("test");
        System.out.println("After decrementing, 'test' should have a count of 0: " + wordCountMap.getCount("test"));
        System.out.println("After decrementing, getNodeCount() should return 1: " + wordCountMap.getNodeCount());

        //tests if proper exception is thrown if word is not found
        try{
            wordCountMap.decrementCount("tes");
            System.out.println("decrementCount() failed to thrown an exception.");
        }
        catch(NullPointerException e){
            System.out.println("decrementCount() successfully threw the exception.");
        }
        wordCountMap.clear();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("TESTS FOR contains(): ");
        //test if testContains() properly returns false for an empty WordCountMap
        System.out.println("Should return false here. contains() returns : " + wordCountMap.contains("test"));

        //test if testContains() properly returns true for a word with a count of 1
        wordCountMap.incrementCount("test");
        System.out.println("Should return true here. contains() returns : " + wordCountMap.contains("test"));
        System.out.println("Should return false here. contains() returns : " + wordCountMap.contains("t"));
        System.out.println("Should return false here. contains() returns : " + wordCountMap.contains("st"));
        System.out.println("Should return false here. contains() returns : " + wordCountMap.contains("est"));

        //tests if "test" remains in map after adding the following
        wordCountMap.incrementCount("tes");
        wordCountMap.incrementCount("tests");
        wordCountMap.incrementCount("t");
        System.out.println("Should return true here. contains() returns : " + wordCountMap.contains("test"));
        wordCountMap.clear();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("TESTS FOR getCount(): ");
        //test if getCount() properly returns 0 for empty list
        System.out.println("The count for 'test' should be 0. getCount() returns : " + wordCountMap.getCount("test"));

        //test if getCount() properly return 3 after adding the following
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");
        System.out.println("The count for 'test' should be 3. getCount() returns : " + wordCountMap.getCount("test"));

        //tests if "test" count remains correct after adding the following words
        wordCountMap.incrementCount("tes");
        wordCountMap.incrementCount("tests");
        wordCountMap.incrementCount("t");
        System.out.println("The count for 'test' should still be 3. getCount() returns : " + wordCountMap.getCount("test"));
        System.out.println("The count for 'tests' should be 1. getCount() returns : " + wordCountMap.getCount("tests"));
        System.out.println("The count for 'tes' should be 1. getCount() returns : " + wordCountMap.getCount("tes"));
        System.out.println("The count for 't' should be 1. getCount() returns : " + wordCountMap.getCount("t"));
        wordCountMap.clear();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("TESTS FOR getNodeCount(): ");
        //checks if getNodeCount() properly returns 1 for empty list.
        System.out.println("getNodeCount() should return 1 here. It returns: " + wordCountMap.getNodeCount());

        //checks if getNodeCount() properly returns 4 after adding one node
        wordCountMap.incrementCount("test");
        System.out.println("getNodeCount() should return 5 here. It returns: " + wordCountMap.getNodeCount());

        //checks if getNodeCount() properly returns () after adding several nodes
        wordCountMap.clear();
        System.out.println("getNodeCount() should return 1 here. It returns: " + wordCountMap.getNodeCount());
        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("tests");
        System.out.println("getNodeCount() should return 6 here. It returns: " + wordCountMap.getNodeCount());
        wordCountMap.incrementCount("testss");
        wordCountMap.incrementCount("testsss");
        wordCountMap.incrementCount("dog");
        wordCountMap.decrementCount("test");
        System.out.println("getNodeCount() should return 11 here. It returns: " + wordCountMap.getNodeCount());
        wordCountMap.clear();

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("TESTS FOR getWordCountsByCount(): ");
        wordCountMap.incrementCount("dog");

        wordCountMap.incrementCount("test");
        wordCountMap.incrementCount("test");

        wordCountMap.incrementCount("tests");
        wordCountMap.incrementCount("tests");
        wordCountMap.incrementCount("tests");

        wordCountMap.incrementCount("testss");
        wordCountMap.incrementCount("testss");
        wordCountMap.incrementCount("testss");
        wordCountMap.incrementCount("testss");

        wordCountMap.incrementCount("testsss");
        wordCountMap.incrementCount("testsss");
        wordCountMap.incrementCount("testsss");
        wordCountMap.incrementCount("testsss");
        wordCountMap.incrementCount("testsss");

        System.out.println("order should be testsss, testss, tests, test, and dog: ");

        List<WordCount> list = wordCountMap.getWordCountsByCount();
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).getWord());
        }
    }
}
