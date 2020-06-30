
import java.util.*;
/*
Class that specifies methods and instance variables about RandomList objects.
 */
public class RandomList{
    //Instance variables
    public static final int MINIMUM_SIZE = -4;
    private static int numberOfInstances;
    private List<Integer> numberList;

    //Constructor that takes one parameter, creates list, and populates it with random numbers between 0 and the minimum size.
    public RandomList(int length) {
        numberList = new ArrayList<Integer>();
        numberOfInstances += 1;
        for (int i = 0; i < length; i++) {
            int num = (int) (Math.random() * (MINIMUM_SIZE - 1));
            numberList.add(num);
        }
    }
    //Returns sum of the list.
    public int getSum(){
        int total = 0;
        for (int i: numberList){
            total += i;
        }
        return total;
    }
    //Returns number of RandomList instances created.
    public static int getInstanceCount(){
        return numberOfInstances;
    }
    //Returns numberList of a RandomList object..
    public List getList(){
        return numberList;
    }
    public static void main(String[] args){
        //Takes in command line argument and uses the parseInt() method in Integer class to convert String input into an int.
        int length = Integer.parseInt(args[0]);

        //Create new RandomList and display its information.
        RandomList list1 = new RandomList(length);
        System.out.println("Numbers in list: " + list1.numberList);
        System.out.println("Sum: " + list1.getSum());
        System.out.println("This program created " + list1.getInstanceCount() + " instance(s)");
    }
}
