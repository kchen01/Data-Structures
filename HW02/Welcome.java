import java.util.Scanner;

public class Welcome {
    public static void main(String[] args) {
        //creates scanner object to get input from user
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Data Structures!");
        System.out.println("Type in your name:");

        //gets input from user and stores as name
        String name = scanner.nextLine();

        System.out.println("Type in your favorite number:");

        //gets input from user and stores as number
        double number = scanner.nextDouble();

        System.out.println("Welcome " + name + ".");

        //checks whether number is + or - and acts accordingly: keep subtracting 3 if +, keep adding 2 if -
        if (number > 0.0) {
            while (number >= 0.0) {
                System.out.println(number);
                number -= 3;
            }
        }
        else {
            while (number <= 0.0) {
                System.out.println(number);
                number += 2;
            }
        }
        System.out.println("The hottest chili pepper in the world is so hot it could kill you.");
        scanner.close();
    }
}


