import java.time.format.DateTimeFormatter;
import java.time.*;

public class Flashcard implements Comparable<Flashcard> {
    private LocalDateTime dueDate;
    private String front;
    private String back;

    /**
    * Creates a new flashcard with the given dueDate, text for the front
    * of the card (front), and text for the back of the card (back).
    * dueDate must be in the format YYYY-MM-DDTHH-MM. For example,
    * 2019-11-04T13:03 represents 1:03PM on November 4, 2019. It's
    * okay if this method crashes if the date format is incorrect.
    * In the format above, the time may be omitted, or the time may be
    * more precise (e.g., seconds or milliseconds may be included).
    * The parse method in LocalDateTime can deal with these situations
    *  without any changes to your code.
    */
    public Flashcard(String dueDate, String front, String back){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.dueDate = LocalDateTime.parse(dueDate, formatter);
        this.front = front;
        this.back = back;
    }
    /**
    * Gets the text for the front of this flashcard.
    */
    public String getFrontText(){
        return front;
    }
    /**
    * Gets the text for the Back of this flashcard.
    */
    public String getBackText(){
        return back;
    }
    /**
    * Gets the time when this flashcard is next due.
    */
    public LocalDateTime getDueDate(){
        return dueDate;
    }
    public void setDueDate(LocalDateTime newDueDate){
        dueDate = newDueDate;
    }
    public int compareTo(Flashcard card){
        // will implement later, we're thinking we will compare by due date
        return this.getDueDate().compareTo(card.getDueDate());

    }
}
