/**
* MazeSquare represents a single square within a Maze.
* @author Anna Rafferty
*/ 
public class MazeSquare {
    //Wall variables
    private boolean hasTopWall = false;
    private boolean hasRightWall = false;
    private boolean visited = false; // A boolean determining whether or not a MazeSquare has been visited, automatically set to false. Only changed by a method.
	private boolean solution = false;	// A boolean determining whether or not a MazeSquare was used in the Solution, automatically set to false. Only changed by a method.
    
    //Location of this square in a larger maze.
    private int row;
    private int col;
		
    /**
     * Constructs a new maze square with walls as configured by
     * descriptor (7 = top and right, | is just right, _ is just
     * top, and * is neither top nor right) and located at the
     * given row and column.
     */
    public MazeSquare(char descriptor, int row, int col) {
        this.row = row;
        this.col = col;
        if(descriptor == '7') {
            hasTopWall = true;
            hasRightWall = true;
        } else if(descriptor == '|') {
            hasTopWall = false;
            hasRightWall = true;
        } else if(descriptor == '_') {
            hasTopWall = true;
            hasRightWall = false;
        } else if(descriptor == '*') {
            hasTopWall = false;
            hasRightWall = false;
        } else {
            hasTopWall = false;
            hasRightWall = false;
            System.err.println("Unrecognized character for MazeSquare description: " + descriptor);		
        }
    }
		
    /**
     * Returns true if this square has a top wall.
     */
    public boolean hasTopWall() {
        return hasTopWall;
    }
		
    /**
     * Returns true if this square has a right wall.
     */
    public boolean hasRightWall() {
        return hasRightWall;
    }
		
    /**
     * Returns the row this square is in.
     */
    public int getRow() {
        return row;
    }
		
    /**
     * Returns the column this square is in.
     */
    public int getColumn() {
        return col;
    }
    /*
    *Used to set a MazeSquare as unvisited, visited = false, done at the beginning
    */
    public void unvisited(){
        visited = false;
    }
    /*
    *Used to set a MazeSquare as visited, visited = true, done whenever a new square is reached by the "maze runner"
    */
    public void visited(){
        visited = true;
    }
    /*
    *A method used to check the value of the boolean visited, returning true or false. Called when looking at MazeSquares to potentially go to, in the solve method of Maze.java
    */
    public boolean hasVisited(){
        return visited;
    }
    /*
    *A method used to set a MazeSquare as one of the MazeSquares used in the solution path, only called at the end, after the Solution Stack has been returned.
    */
    public void setSolution(){
        solution = true;
    }
    /*
    *A method used to check whether a MazeSquare has been used for the solution, only used in the print method in Maze.java, when iterating through all of the MazeSquares.
    */
    public boolean isSolution(){
        return solution;
    }
    
    /**
     * Returns true if c is a valid identifier for a maze square
     */
    public static boolean isAllowedCharacter(char c) {
        return c=='*' || c=='_' || c=='|' || c=='7';
    }
		
} 