import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

/**
* Maze represents a maze that can be navigated. The maze
* should indicate its start and end squares, and where the
* walls are. 
*
* Eventually, this class will be able to load a maze from a
* file, and solve the maze.
* The starter code has part of the implementation of load, but
* it does not read and store the information about where the walls of the maze are.
*
*/
public class Maze { 
    //Number of rows in the maze.
    private int numRows;
    
    //Number of columns in the maze.
    private int numColumns;
    
    //Grid coordinates for the starting maze square
    private int startRow;
    private int startColumn;
    
    //Grid coordinates for the final maze square
    private int finishRow;
    private int finishColumn;
    
 
    private ArrayList<MazeSquare> mazeSquares; // An arraylist holding all of our mazesquares, of type MazeSquare
    private char descriptor;
    private Stack<MazeSquare> solution;

    /**
     * Creates an empty maze with no squares.
     */
    public Maze() {
        
        mazeSquares = new ArrayList<MazeSquare>(); //You can add any code you need to initialize instance 
          //variables you've added.
    } 
    
    /**
     * Loads the maze that is written in the given fileName.
     */
    public boolean load(String fileName) { 
        Scanner scanner = null;
        try {
            //Open a scanner to read the file
            scanner = new Scanner(new File(fileName));
            numColumns = scanner.nextInt();
            numRows = scanner.nextInt();
            startColumn = scanner.nextInt();
            startRow = scanner.nextInt();
            finishColumn = scanner.nextInt();
            finishRow = scanner.nextInt();
            
            //Check if the start or finish squares are out of bounds
            if(!isInRange(startRow, 0, numRows) 
                    || !isInRange(startColumn, 0, numColumns)
                    || !isInRange(finishRow, 0, numRows) 
                    || !isInRange(finishColumn, 0, numColumns)) {
                System.err.println("Start or finish square is not in maze.");
                scanner.close();
                return false;
            }
            /*
            *for every line in the maze.txt, or whatever the file is named, creates a MazeSquare at each of the specific rows and columns, columns changing every character, and rows changing every line. This starts AFTER the number of total rows and columns have been scanned, and the finish and start Squares have been determined.
            **/
            for(int row = 0; row < numRows; row ++){
                String line = scanner.next();
                for(int col = 0; col < numColumns; col ++){
                    descriptor = line.charAt(col);
                    MazeSquare square = new MazeSquare(descriptor, row, col);
                    mazeSquares.add(((row * numColumns) + col),square);
                }
            }
        } catch(FileNotFoundException e) {
            System.err.println("The requested file, " + fileName + ", was not found.");
            return false;
        } catch(InputMismatchException e) {
            System.err.println("Maze file not formatted correctly.");
            scanner.close();
            return false;
        } 
        
        return true;
    } 
    
    /**
     * Returns true if number is greater than or equal to lower bound
     * and less than upper bound. 
     * @param number
     * @param lowerBound
     * @param upperBound
     * @return true if lowerBound ≤ number < upperBound
     */
    private static boolean isInRange(int number, int lowerBound, int upperBound) {
        return number < upperBound && number >= lowerBound;
    }
    
    /**
     * Prints the maze with the start and finish squares marked. Does
     * include a solution, if there is one, through booleans created and defined in MazeSquare, and changed in the main.
     */
    public void print() {
        //We'll print off each row of squares in turn.
        for(int row = 0; row < numRows; row++) {
            
            //Print each of the lines of text in the row
            for(int charInRow = 0; charInRow < 4; charInRow++) {
                //Need to start with the initial left wall.
                if(charInRow == 0) {
                    System.out.print("+");
                } else {
                    System.out.print("|");
                }
                
                for(int col = 0; col < numColumns; col++) {
                    MazeSquare curSquare = this.getMazeSquare(row, col);
                    if(charInRow == 0) {
                        //We're in the first row of characters for this square - need to print
                        //top wall if necessary.
                        if(curSquare.hasTopWall()) {
                            System.out.print(getTopWallString());
                        } else {
                            System.out.print(getTopOpenString());
                        }
                    } else if(charInRow == 1 || charInRow == 3) {
                        //These are the interior of the square and are unaffected by
                        //the start/final state.
                        if(curSquare.hasRightWall()) {
                            System.out.print(getRightWallString());
                        } else {
                            System.out.print(getOpenWallString());
                        }
                    } else {
                        //We must be in the second row of characters.
                        //This is the row where start/finish should be displayed if relevant
                        
                        //Check if we're in the start or finish state
                        if(startRow == row && startColumn == col) {
                            System.out.print("  S  ");
                        } else if(finishRow == row && finishColumn == col) {
                            System.out.print("  F  ");
                        } else if(curSquare.isSolution()){ // ADDED THIS OURSELVES - this prints if a boolean(solution) is true using the method isSolution() which we added to MazeSquare.java
                            System.out.print("  *  ");
                        }else {
                            System.out.print("     ");
                        }
                        if(curSquare.hasRightWall()) {
                            System.out.print("|");
                        } else {
                            System.out.print(" ");
                        }
                    } 
                }
                
                //Now end the line to start the next
                System.out.print("\n");
            }           
        }
        
        //Finally, we have to print off the bottom of the maze, since that's not explicitly represented
        //by the squares. Printing off the bottom separately means we can think of each row as
        //consisting of four lines of text.
        printFullHorizontalRow(numColumns);
    }
    
    /**
     * Prints the very bottom row of characters for the bottom row of maze squares (which is always walls).
     * numColumns is the number of columns of bottom wall to print.
     */
    private static void printFullHorizontalRow(int numColumns) {
        System.out.print("+");
        for(int row = 0; row < numColumns; row++) {
            //We use getTopWallString() since bottom and top walls are the same.
            System.out.print(getTopWallString());
        }
        System.out.print("\n");
    }
    
    /**
     * Returns a String representing the bottom of a horizontal wall.
     */
    private static String getTopWallString() {
        return "-----+";
    }
    
    /**
     * Returns a String representing the bottom of a square without a
     * horizontal wall.
     */
    private static String getTopOpenString() {
        return "     +";
    }
    
    /**
     * Returns a String representing a left wall (for the interior of the row).
     */
    private static String getRightWallString() {
        return "     |";
    }
    
    /**
     * Returns a String representing no left wall (for the interior of the row).
     */
    private static String getOpenWallString() {
        return "      ";
    }
    
    /**
     * takes a row and column and returns the MazeSquare at that row and column value, from our ArrayList of MazeSquares
     * "mazeSquares"
     */
    public MazeSquare getMazeSquare(int row, int col) {
        if(row == 0){
            return mazeSquares.get(col);
        }else{
            return mazeSquares.get(col + (numColumns * row));
        }
        
    }
    /*
    *solves the maze, and returns a stack of the solved maze "moves" between each maze square, in a type Stack<MazeSquare>
    *
    */
    public Stack<MazeSquare> getSolution(){
        boolean solved = false;
        for(MazeSquare square: mazeSquares){
            square.unvisited();
        }
        solution = new MysteryStackImplementation<MazeSquare>();
        solution.push(this.getMazeSquare(startRow, startColumn));
        MazeSquare currentSquare = this.getMazeSquare(startRow, startColumn);
        currentSquare.visited(); // sets the starting square as visited
        int timesthrough = 0;
        while(!solved){
        
        if (solution.isEmpty()){
            System.out.println("There is no Solution");
            return solution;
        }else if(solution.peek().equals(this.getMazeSquare(finishRow, finishColumn))){
            System.out.println("Solution Found!");
            return(solution);
        }else{
           currentSquare = solution.peek();
            //Checks to see if the current MazeSquare has a top wall, and if not, whether or not the MazeSquare above it has been visited, and then pushes the square above it to Stack<arraylist> solution, as well as sets the square above it to currentSquare.
            if(currentSquare.hasTopWall() == false){
                MazeSquare checkSquare = this.getMazeSquare(currentSquare.getRow() - 1, currentSquare.getColumn());
                if(checkSquare.hasVisited() == false){
                    currentSquare = checkSquare; 
                    solution.push(currentSquare);
                    currentSquare.visited();
                    continue;
                }} 
            // Checks to see if the Current MazeSquare has a right wall, and if not, whether the MazeSquare to the right of it has been visited, and then pushes the square to the right of it to Stack<arraylist> solution, as well as sets the square to the right of it to currentSquare.
            if(currentSquare.hasRightWall() == false){ 
                MazeSquare checkSquare = this.getMazeSquare(currentSquare.getRow(), currentSquare.getColumn() + 1);
                if(checkSquare.hasVisited() == false){
                    currentSquare = checkSquare;
                    solution.push(currentSquare);
                    currentSquare.visited();
                    continue;
                }} 
            // Checks to see if the Current MazeSquare has a left wall, and if not, whether the MazeSquare to the left of it has been visited, and then pushes the square to the left of it to Stack<arraylist> solution, as well as sets the square to the left of it to currentSquare. it does this by checking to see if the square to the left of it has a right wall and has been visited.    
            if(currentSquare.getColumn() >= 1){
                MazeSquare checkSquare = this.getMazeSquare(currentSquare.getRow(), currentSquare.getColumn() - 1);
                if(checkSquare.hasRightWall() == false){
                if(checkSquare.hasVisited() == false){
                    currentSquare = checkSquare;
                    solution.push(currentSquare);
                    currentSquare.visited();
                    continue;
                }}} 
            // Checks to see if the Current MazeSquare has a bottom wall, and if not, whether the MazeSquare below it has been visited, and then pushes the square beneath it to Stack<arraylist> solution, as well as sets the square underneath it to currentSquare. It does this by checking to see if the square underneath it has an upper wall or whether or not it has been visited.
            if(currentSquare.getRow() < (numRows - 1)){
                MazeSquare checkSquare = this.getMazeSquare(currentSquare.getRow() + 1, currentSquare.getColumn());
                if(checkSquare.hasTopWall() == false){
                    if(checkSquare.hasVisited() == false){
                    currentSquare = checkSquare;
                    solution.push(currentSquare);
                    currentSquare.visited();
                    continue;
                }}}
       
        
        
        solution.pop(); // all of the if statements have continue in them, meaning if one of them succeed, the while loop will restart. If by some reason(there are no more paths possible to take) the while loop reaches the end of its statement, it will go back to the previous square, while the current square stays Visited.
        }
        }        
    return solution;  
    }
    
 
    /**
     * You should modify main so that if there is only one
     * command line argument, it loads the maze and prints it
     * with no solution. If there are two command line arguments
     * and the second one is --solve,
     * it should load the maze, solve it, and print the maze
     * with the solution marked. No other command lines are valid.
     */ 
    public static void main(String[] args) { 
        Maze maze = new Maze();
        String textFile = args[0];
        maze.load(textFile);
        if(args.length == 2){
        if (args[1].equals("[--solve]")){
            Stack<MazeSquare> solution = maze.getSolution(); 
             while(solution.isEmpty() == false){
                solution.pop().setSolution();
            }
            

     maze.print();   
    }else{
            System.out.println("Your second input was an invalid input. Please try again, using only '[--solve]', or only having one command line argument: The text file.");
        } 
    }else{
        maze.print();
        }
}
}