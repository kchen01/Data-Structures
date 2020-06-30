
/*
Class that uses the RandomList class to perform operations.
*/
public class RandomListClient {
    public static void main(String[] args){
        boolean lessThanTen = false;
        //Loops until a loop with a sum of less than 10 is created and then displays its information.
        while (lessThanTen == false){
            RandomList list = new RandomList(5);
            if (list.getSum() < -10){
                lessThanTen = true;
                System.out.println("Numbers in list: " + list.getList());
                System.out.println("Sum: " + list.getSum());
                System.out.println("This program created " + RandomList.getInstanceCount() + " instance(s)");
            }
        }
    }
}
