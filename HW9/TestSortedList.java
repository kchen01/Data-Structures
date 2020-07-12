import java.util.*;
import java.io.*;

public class TestSortedList{
    public static void testRemoveIndex(){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String country1Line = textScan.nextLine();
        String[] country1Array = country1Line.split(",");
        Country country1 = new Country(country1Array); //afghanistan

        String country2Line = textScan.nextLine();
        String[] country2Array = country2Line.split(",");
        Country country2 = new Country(country2Array);

        String country3Line = textScan.nextLine();
        String[] country3Array = country3Line.split(",");
        Country country3 = new Country(country3Array);

        CountryComparator comparator = new CountryComparator("CO2Emissions", false);
        SortedLinkedList list = null;

        //tests if remove() will throw an error if a negative number is passed in.
        try{
          list = new SortedLinkedList(comparator);
          list.add(country1);
          list.remove(-1);
          System.out.println("Remove method failed to catch an Exception for using remove on a negative index.");
        }
        catch(IndexOutOfBoundsException e){
          System.out.println("Remove method successfully caught an IndexOutOfBoundsException for using remove on a negative index.");
        }
        // tests if remove() will throw an error if a number greater than the size of the SortedLinkedList is passed in.
        try{
          list = new SortedLinkedList(comparator);
          list.add(country1);
          list.remove(2);
          System.out.println("Remove method failed to throw an error for using remove on out of bounds index.");
        }
        catch(IndexOutOfBoundsException e){
          System.out.println("Remove method successfully caught an IndexOutOfBoundsException for using remove on out of bounds index.");
        }
        //tests if remove() will throw an error if it tries to remove from an empty list.
        try{
          list = new SortedLinkedList(comparator);
          list.remove(0);
          System.out.println("Remove method failed to throw an error for trying to remove from empty list.");
        }
        catch (Exception e){
            System.out.println("Remove method successfully threw an error for trying to remove from empty list.");
        }
        // tests if method properly removes the first index
        try{
          list = new SortedLinkedList(comparator);
          list.add(country1);
          list.add(country2);
          list.add(country3);
          list.remove(0);

          if (list.size() == 2){
              System.out.println("Remove method successfully removed the first index.");
          }
          else{
              System.out.println("Remove method failed to remove the first index.");
          }
        }
        catch (Exception e){
            System.out.println("Remove method failed to remove the first index.");
        }
        // tests if method properly removes the last index
        try{
          list = new SortedLinkedList(comparator);
          list.add(country1);
          list.add(country2);
          list.add(country3);
          list.remove(2);

          if (list.size() == 2){
              System.out.println("Remove method successfully removed the last index.");
          }
          else{
              System.out.println("Remove method failed to remove the last index.");
          }
        }
        catch (Exception e){
            System.out.println("Remove method failed to remove the last index.");
        }
        // tests if method properly removes a middle index
        try{
          list = new SortedLinkedList(comparator);
          list.add(country1);
          list.add(country2);
          list.add(country3);
          list.remove(1);

          if (list.size() == 2){
              System.out.println("Remove method successfully removed the middle index.");
          }
          else{
              System.out.println("Remove method failed to remove the middle index.");
          }
        }
        catch (Exception e){
            System.out.println("Remove method failed to remove the middle index.");
        }
    }
    public static void testRemoveCountry(){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String country1Line = textScan.nextLine();
        String[] country1Array = country1Line.split(",");
        Country country1 = new Country(country1Array); //afghanistan

        String country2Line = textScan.nextLine();
        String[] country2Array = country2Line.split(",");
        Country country2 = new Country(country2Array);

        String country3Line = textScan.nextLine();
        String[] country3Array = country3Line.split(",");
        Country country3 = new Country(country3Array);

        CountryComparator comparator = new CountryComparator("CO2Emissions", false);
        SortedLinkedList list = null;

        //tests if remove() will return false if it tries to remove from an empty list.
        list = new SortedLinkedList(comparator);
        System.out.println("method should return false for trying to remove from empty list. method returns : " + list.remove(country1));

        //tests if remove() will throw an error if targetCountry isn't in list.
        list = new SortedLinkedList(comparator);
        list.add(country1);
        if (list.remove(country2) == false){
            System.out.println("Remove method properly returned false for trying to remove country not in list.");
        }
        else{
            System.out.println("Remove method failed to return false for trying to remove country not in list.");
        }
        //tests if remove() will properly remove a node at the beginning.
          list = new SortedLinkedList(comparator);
          list.add(country1);
          list.add(country2);
          list.add(country3);
          list.remove(country1);
          if (list.size() == 2){
              System.out.println("Remove method properly removed node at beginning of list.");
          }
          else{
              System.out.println("Remove method failed to properly remove node at beginning of list.");
          }
        //tests if remove() will properly remove a node at the middle.
          list = new SortedLinkedList(comparator);
          list.add(country1);
          list.add(country2);
          list.add(country3);
          list.remove(country2);
          if (list.size() == 2){
              System.out.println("Remove method properly removed node in middle of list.");
          }
          else{
              System.out.println("Remove method failed to properly remove node in middle of list.");
          }
    }
    public static void testAdd(){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String country1Line = textScan.nextLine();
        String[] country1Array = country1Line.split(",");
        Country country1 = new Country(country1Array); //afghanistan

        String country2Line = textScan.nextLine();
        String[] country2Array = country2Line.split(",");
        Country country2 = new Country(country2Array);

        String country3Line = textScan.nextLine();
        String[] country3Array = country3Line.split(",");
        Country country3 = new Country(country3Array);

        CountryComparator comparator = new CountryComparator("CO2Emissions", false);
        SortedLinkedList list = null;

        //tests if method properly adds to an empty list
          list = new SortedLinkedList(comparator);
          list.add(country1);
          if (list.size() == 1){
              System.out.println("Remove method successfully added node to empty list.");
          }
          else{
              System.out.println("Remove method failed to add node to empty list.");
          }
        }

    public static void testSize(){
      File text = new File("CountryDataset.csv");
      Scanner textScan = null;
      String line = "";
      try {
          textScan = new Scanner(text);
      } catch (FileNotFoundException e) {
          System.err.println(e);
          System.exit(1);
      }
      if(textScan.hasNextLine()){
         line = textScan.nextLine();
      } else {
          System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
          System.exit(1);
      }
      String country1Line = textScan.nextLine();
      String[] country1Array = country1Line.split(",");
      Country country1 = new Country(country1Array); //afghanistan

      String country2Line = textScan.nextLine();
      String[] country2Array = country2Line.split(",");
      Country country2 = new Country(country2Array);

      String country3Line = textScan.nextLine();
      String[] country3Array = country3Line.split(",");
      Country country3 = new Country(country3Array);

      CountryComparator comparator = new CountryComparator("CO2Emissions", false);
      SortedLinkedList list = null;

      //tests to see is size() properly returns 0 for an empty list.
      list = new SortedLinkedList(comparator);
      if (list.size() == 0){
          System.out.println("size() properly returns 0 for an empty list");
      }
      else{
          System.out.println("size() fails to return 0 for an empty list.");
      }
      //tests to see is size() properly returns 1 for a list of size 1.
      list = new SortedLinkedList(comparator);
      list.add(country1);
      if (list.size() == 1){
          System.out.println("size() properly returns 1 for a list of size 1");
      }
      else{
          System.out.println("size() fails to return 1 for a list of size 1.");
      }
      //tests to see is size() properly returns 3 for a list of size 3.
      list = new SortedLinkedList(comparator);
      list.add(country1);
      list.add(country2);
      list.add(country3);
      if (list.size() == 3){
          System.out.println("size() properly returns 3 for a list of size 3");
      }
      else{
          System.out.println("size() fails to return 3 for a list of size 3.");
      }
    }
    public static void testIsEmpty(){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String country1Line = textScan.nextLine();
        String[] country1Array = country1Line.split(",");
        Country country1 = new Country(country1Array); //afghanistan

        CountryComparator comparator = new CountryComparator("CO2Emissions", false);
        SortedLinkedList list = null;

        list = new SortedLinkedList(comparator);
        System.out.println("isEmpty() should return true here. list.isEmpty() returns: " + list.isEmpty());

        list.add(country1);

        System.out.println("isEmpty() should return false here. list.isEmpty() returns: " + list.isEmpty());
    }
    public static void testClear(){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String country1Line = textScan.nextLine();
        String[] country1Array = country1Line.split(",");
        Country country1 = new Country(country1Array); //afghanistan

        String country2Line = textScan.nextLine();
        String[] country2Array = country2Line.split(",");
        Country country2 = new Country(country2Array);

        String country3Line = textScan.nextLine();
        String[] country3Array = country3Line.split(",");
        Country country3 = new Country(country3Array);

        CountryComparator comparator = new CountryComparator("CO2Emissions", false);
        SortedLinkedList list = null;
        list = new SortedLinkedList(comparator);
        list.add(country1);
        list.add(country2);
        list.add(country3);
        list.clear();
        System.out.println("List should be empty here after using clear(). list.isEmpty() returns: " + list.isEmpty());
    }
    public static void testGetPosition(){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String country1Line = textScan.nextLine();
        String[] country1Array = country1Line.split(",");
        Country country1 = new Country(country1Array); //afghanistan

        String country2Line = textScan.nextLine();
        String[] country2Array = country2Line.split(",");
        Country country2 = new Country(country2Array);

        String country3Line = textScan.nextLine();
        String[] country3Array = country3Line.split(",");
        Country country3 = new Country(country3Array);

        String country4Line = textScan.nextLine();
        String[] country4Array = country4Line.split(",");
        Country country4 = new Country(country4Array);

        CountryComparator comparator = new CountryComparator("CO2Emissions", false);
        SortedLinkedList list = null;
        list = new SortedLinkedList(comparator);

        list.add(country1);
        list.add(country2);
        list.add(country3);

        System.out.println("Beginning: getPosition() method should return 0 here. Method returns : " + list.getPosition(country1));
        System.out.println("Middle: getPosition() method should return 1 here. Method returns : " + list.getPosition(country2));
        System.out.println("End: getPosition() method should return 2 here. Method returns : " + list.getPosition(country3));
        System.out.println("getPosition() method should return -1 here since country isn't in list. Method returns : " + list.getPosition(country4));
    }
    public static void testGet(){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String country1Line = textScan.nextLine();
        String[] country1Array = country1Line.split(",");
        Country country1 = new Country(country1Array); //afghanistan

        String country2Line = textScan.nextLine();
        String[] country2Array = country2Line.split(",");
        Country country2 = new Country(country2Array);

        String country3Line = textScan.nextLine();
        String[] country3Array = country3Line.split(",");
        Country country3 = new Country(country3Array);

        String country4Line = textScan.nextLine();
        String[] country4Array = country4Line.split(",");
        Country country4 = new Country(country4Array);

        CountryComparator comparator = new CountryComparator("CO2Emissions", false);
        SortedLinkedList list = null;
        list = new SortedLinkedList(comparator);

        list.add(country1);
        list.add(country2);
        list.add(country3);

        try{
            list.get(-1);
            System.out.println("get() failed to throw an IndexOutOfBoundsException for an out of bounds index.");
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("get() properly threw an IndexOutOfBoundsException for an out of bounds index.");
        }
        System.out.println("Beginning: get() should return 'Afghanistan' here. get() returns " + list.get(0).getName());
        System.out.println("Middle: get() should return 'Albania' here. get() returns " + list.get(1).getName());
        System.out.println("End: get() should return 'Algeria' here. get() returns " + list.get(2).getName());

    }
    public static void testContains(){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String country1Line = textScan.nextLine();
        String[] country1Array = country1Line.split(",");
        Country country1 = new Country(country1Array); //afghanistan

        String country2Line = textScan.nextLine();
        String[] country2Array = country2Line.split(",");
        Country country2 = new Country(country2Array);

        String country3Line = textScan.nextLine();
        String[] country3Array = country3Line.split(",");
        Country country3 = new Country(country3Array);

        String country4Line = textScan.nextLine();
        String[] country4Array = country4Line.split(",");
        Country country4 = new Country(country4Array);

        CountryComparator comparator = new CountryComparator("CO2Emissions", false);
        SortedLinkedList list = null;
        list = new SortedLinkedList(comparator);

        list.add(country1);
        list.add(country2);
        list.add(country3);

        System.out.println("contains() should return false if the country isn't in list. contains() returns: " + list.contains(country4));
        System.out.println("contains() should return true for the first country in the list. contains() returns : " + list.contains(country1));
        System.out.println("contains() should return true for a middle country in the list. contains() returns : " + list.contains(country2));
        System.out.println("contains() should return true for the last country in the list. contains() rseturns : " + list.contains(country3));
    }
    public static void testToArray(){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String country1Line = textScan.nextLine();
        String[] country1Array = country1Line.split(",");
        Country country1 = new Country(country1Array); //afghanistan

        String country2Line = textScan.nextLine();
        String[] country2Array = country2Line.split(",");
        Country country2 = new Country(country2Array);

        String country3Line = textScan.nextLine();
        String[] country3Array = country3Line.split(",");
        Country country3 = new Country(country3Array);

        CountryComparator comparator = new CountryComparator("CO2Emissions", false);
        SortedLinkedList list = null;
        list = new SortedLinkedList(comparator);

        System.out.println("The length of this empty array should be 0: " + list.toArray().length);

        list.add(country1);
        list.add(country2);
        list.add(country3);

        System.out.println("The length of this array should be 3: " + list.toArray().length);
    }
    /*
    public static void testResort(){
        File text = new File("CountryDataset.csv");
        Scanner textScan = null;
        String line = "";
        try {
            textScan = new Scanner(text);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        if(textScan.hasNextLine()){
           line = textScan.nextLine();
        } else {
            System.out.println("The file is empty. You should input a properly formatted country file which has the first line indicating the meaning of each field.");
            System.exit(1);
        }
        String country1Line = textScan.nextLine();
        String[] country1Array = country1Line.split(",");
        Country country1 = new Country(country1Array); //afghanistan

        String country2Line = textScan.nextLine();
        String[] country2Array = country2Line.split(",");
        Country country2 = new Country(country2Array);

        String country3Line = textScan.nextLine();
        String[] country3Array = country3Line.split(",");
        Country country3 = new Country(country3Array);

        CountryComparator comparator = new CountryComparator("CO2Emissions", false);
        SortedLinkedList list = null;
        list = new SortedLinkedList(comparator);

        list.add(country1);
        list.add(country2);
        list.add(country3);

        System.out.println("This is the list before resorting.");

        Node curNode = list.getHead();
        for(int i = 0; i < 3; i++){
            System.out.println(curNode.getCountry().getName());
            curNode = curNode.next;
        }
        comparator = new CountryComparator("CO2Emissions", true);

        list.resort(comparator);

        System.out.println("This is the list after resorting.");

        Node curNode = list.getHead();
        for(int i = 0; i < 3; i++){
            System.out.println(curNode.getCountry().getName());
            curNode = curNode.next;
        }
    }
    */

    public static void main(String[] args){
       //testRemoveIndex();
       //testAdd();
       //testRemoveCountry();
       //testClear();
       //testGetPosition();
       //testGet();
       //testContains();
       //testSize();
       //testToArray();
       //testIsEmpty();
       //testResort();

    }

}
