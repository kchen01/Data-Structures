import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/******************
 * Program - A class to represent a Java program within the
 * VM Simulator
 *
 * @author Andy
 *
 */
public class Program {
    /*************
     * A simple syntax-checking mechanism that makes sure our programs have matching
     * parentheses (), brackets [], and squiggly-brackets {}.
     * To be implemented by students.
     * @param mScanner the scanner passed in from the constructor
     * @throws SyntaxErrorException if a syntax error is encountered, with a helpful message.
     * @return true if syntax passes.
     */
    private boolean syntaxCheck(Scanner mScanner) throws SyntaxErrorException {
        CircularArrayDequeImplementation<Character> deque = new CircularArrayDequeImplementation<Character>(); //deque used to check matching symbols
        String line = ""; // to store each line being read through
        char curChar; //to store the current char that is being looked at within each line
        while (mScanner.hasNextLine()) {
                // while there is another line in the file, set line to next line
                line = mScanner.nextLine();
                //this loop goes through each char in the line, skipping line if it is a comment, pushing any starting symbols
                // onto the stack and throwing an error if there is a closing bracket without a matching start bracket at the top of the stack.
                for(int i = 0; i < line.length(); i ++){
                    //set curChar to next char in line
                    curChar = line.charAt(i);
                    // if there are two consecutive '/' characters, line is set to next line, i is reset to 0,
                    //and curChar is reset to begnning of line
                    if (curChar == '/' && line.charAt(i + 1) == '/'){
                        line = mScanner.nextLine();
                        i = 0;
                        curChar = line.charAt(i);
                    }
                    //switch case that takes different actions depending on the curChar
                    switch(curChar){
                        //push starting brackets to stack
                        case '[':
                            deque.addFront(curChar);
                             break;
                        case '(':
                            deque.addFront(curChar);
                            break;
                        case '{':
                            deque.addFront(curChar);
                            break;
                        // for closing brackets, check if top of stack is a matching starting bracket.
                        //If yes, pop the stack. If not, throw an error.
                        case ']':
                            if (deque.peekFront() != '['){
                                throw new SyntaxErrorException("There is a ']' without a '['. ");
                            }
                            else{
                                deque.removeFront();
                                break;
                            }
                        case ')':
                            if (deque.peekFront() != '('){
                                throw new SyntaxErrorException("There is a ')' without a '('. ");
                            }
                            else{
                                deque.removeFront();
                                break;
                            }
                        case '}':
                            if (deque.peekFront() != '{'){
                                throw new SyntaxErrorException("There is a '}' without a '{'. ");
                            }
                            else{
                                deque.removeFront();
                                break;
                            }
                      }
                }
        }
        return true;
    }
    /*******************
     * Program's constructor. Dynamically tries to instantiate its call stack...
     * If you haven't written class CallStack yet, it won't bomb.
     * @param mFile The file to read for the program.
     * @param mComp The simulation component that creates this program
     * @throws SyntaxErrorException
     */
    public Program(File mFile, SimulationComponent mComp) throws SyntaxErrorException {
        callStack = instantiate(mComp);
        bFinished = false;
        gName = mFile.getName();
        Scanner s = null;
        try {
            s = new Scanner(mFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error: File " + mFile.getName() + " not found!");
            e.printStackTrace();
            System.exit(1);
        }
        if(syntaxCheck(s)) {
            s.close();
            try {
                s = new Scanner(mFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            loadMethodList(s);
            gMethodList.get(0).setStack(callStack);
        }
    }

    /************
     * Should only be called by unit test classes
     * @param mProgramString
     * @throws SyntaxErrorException
     */
    protected Program(String mProgramString) throws SyntaxErrorException {
        gName = "Unnamed program";
        Scanner s = new Scanner(mProgramString);
        if(syntaxCheck(s)) {
            s = new Scanner(mProgramString);
            loadMethodList(s);
        }
    }

    /***********
     * Gets the name of this program
     * @return
     */
    public String getName() { return gName;  }

    private Deque<Method> callStack;

    private void loadMethodList(Scanner mScanner) {
        String tLine = null;
        gMethodList = new LinkedList<Method>();

        while(mScanner.hasNextLine()) {
            tLine = mScanner.nextLine();
            Pattern tPattern = Pattern.compile("def .*?(\\w*)\\(.*?\\)\\s*(\\{?)");
            Matcher tM1 = tPattern.matcher(tLine);
            if(tM1.matches()) {
                Method tMethod = new Method(tM1.group(1), this);
                int bracketcounter = tM1.group(2).equals("{")?1:0;
                while(bracketcounter > 0 && mScanner.hasNextLine()) {
                    tLine = mScanner.nextLine();
                    tMethod.addLine(tLine);
                    char tPrevChar = ' ';
                    for(int i=0; i<tLine.length(); i++) {
                        char tChar = tLine.charAt(i);
                        if(tChar == '/' && tPrevChar == '/') { i = tLine.length(); }
                        else if(tChar == '{') { bracketcounter++; }
                        else if(tChar == '}') { bracketcounter--; }
                        tPrevChar = tChar;
                    }
                }
                gMethodList.add(tMethod);
            }
        }
    }

    // Name of the program's file
    private String gName;

    // ordered list of Methods that this Program calls
    private List<Method> gMethodList;

    private boolean bFinished;
    private boolean gStarted = false;

    public List<Method> getMethodList() { return gMethodList; }

    /***********
     * Gets a method given a name
     * @param mName the name of the Method to retrieve
     * @return the method
     */
    public Method getMethod(String mName) {
        for(Method tMeth: gMethodList) {
            if(tMeth.getName().equals(mName)) {
                Method ret = tMeth.copy();
                ret.setStack(callStack);
                return ret;
            }
        }
        return null;
    }

    /******************
     * Executes one step of this method, using the given SimulationComponent to display
     * what is going on
     * @param mComp the SimulationComponent in which to display things
     */
    public void step(SimulationComponent mComp) {
        if(callStack == null) {
            if(!gMethodList.get(0).isFinished()) {
                gMethodList.get(0).step(mComp);
            } else { bFinished = true; }
            return;
        }
        if(!gStarted && callStack != null) {
            callStack.addFront(gMethodList.get(0));
            gStarted = true;
        }
        if(callStack != null && !callStack.isEmpty()) {
            if(callStack.peekFront().isFinished()) {
                callStack.removeFront();
            } else {
                callStack.peekFront().step(mComp);
            }
        }
        else {
            bFinished = true;
        }
    }

    /*************
     * Returns whether this program is finished executing
     * @return true if the program is finished
     */
    public boolean isFinished() {
        return bFinished;
    }

    /************
     * Crazy little method to dynamically instantiate CallStack class
     * @param mComp
     * @return an instantiation of CallStack, or null if failed
     */
    @SuppressWarnings("unchecked")
    private Deque<Method> instantiate(SimulationComponent mComp) {
        String problem = "";
        try {
            Constructor<?> m = Class.forName("CallStack").getConstructor(SimulationComponent.class);
            System.err.println("Call Stack instantiated... will attempt to run on VM!");
            return (Deque<Method>) m.newInstance(mComp);
        } catch (SecurityException e) {
            problem = "Access to constructor denied. (SecurityException)";
        } catch (NoSuchMethodException e) {
            problem = "No constructor found for CallStack that takes a SimluationComponent as a parameter. (NoSuchMethodException)";
        } catch (ClassNotFoundException e) {
            problem = "The CallStack class doesn't exist yet (ClassNotFoundException)";
        } catch (IllegalArgumentException e) {
            problem = "Wrong number of arguments to the constructor (IllegalArgumentException)";
        } catch (InstantiationException e) {
            problem = "CallStack appears to be an abstract class --- maybe it doesn't implement all the\n" +
                    "   methods of the interface it claims to implement. (InstantiationException)";
        } catch (IllegalAccessException e) {
            problem = "Constructor is inaccessible. (IllegalAccessException)";
        } catch (InvocationTargetException e) {
            problem = "Constructor threw an exception. (InvocationTargetException)";
        }
        System.err.println("Cannot instantiate CallStack, method calls won't work.");
        System.err.println(problem);
        return null;
    }

    /*****
     * simple main for testing
     *****/
    public static void main(String[] args) {
        try {
            String prog = "";
            Scanner s = new Scanner(new File(args[0]));
            while (s.hasNextLine()) { prog += s.nextLine() + "\n"; }
            Program p = new Program(prog);
            for(Method m : p.getMethodList()) {
                System.out.println(m.getName());
            }
        } catch(SyntaxErrorException e) {

        } catch(FileNotFoundException e1) {

        }
    }
}
