import java.util.*;
import java.io.*;

public class FileDumper{
    public static void main(String[] args){
        String filePath = args[0];
        Scanner scanner = null;
        String line = "";
        File file = new File(filePath);
        int lineNumber = 0;
        try{
            char character;
            int asciiValue;
            String hex;
            int j = 0;
            String dumped = "";

            scanner = new Scanner(file);
            System.out.println("File name: " + filePath);

            for(int i = 0; i < 3; i++){
                line = scanner.nextLine();
                if (i == 0){ // the very first line
                    System.out.println();
                    System.out.print("0:" + "\t");
                }
                for(j = 0; j < line.length(); j++){
                    // if it's not the very first time and it is the beginning of the next line OR if j has a factor of 10 but not 0
                    if ((i != 0 && j == 0) || (j!= 0 && (j % 20 == 0))){
                        System.out.println(" " + dumped);
                        dumped = "";
                        System.out.println();
                        System.out.print((lineNumber + j) + ": " + "\t");
                    }
                    character  = line.charAt(j);
                    dumped = dumped.concat(String.valueOf(character));
                    asciiValue = (int) character;
                    hex = String.format("%04x", (int) character);
                    //System.out.print(asciiValue + " ");
                    System.out.print(hex + " ");
                }
                lineNumber += j;
            }
            System.out.println(" " + dumped);
        }
        catch(FileNotFoundException e){
            System.out.println("File not found.");
        }
    }
}
