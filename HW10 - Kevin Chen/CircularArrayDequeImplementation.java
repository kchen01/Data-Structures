import java.util.*;

public class CircularArrayDequeImplementation<T>  implements Deque<T> {
    private T[] items;
    private int frontIndex;
    private int backIndex;
    private int numItems;
    public static final int DEFAULT_SIZE = 10;
    /**
    Constructor for the CircularArrayDequeImplementation class
    */
    public CircularArrayDequeImplementation(){
        @SuppressWarnings("unchecked")
        T[] tmp = (T[]) new Object[DEFAULT_SIZE +1];
        items = tmp;
        numItems = 0;
        frontIndex = 0;
        backIndex = DEFAULT_SIZE;
    }
    /** Adds an item to the front of this deque
     * @param item The item to add.
     */
    public void addFront(T item){
        //if full, resize the array, add item, and increment numItems
        if (this.isFull()){
            this.resize();
            frontIndex = items.length - 1;
            items[frontIndex] = item;
            numItems += 1;
        }
        //if empty, assign item to frontIndex, make both indexes refer to same item, and increment numItems
        else if (this.isEmpty()){
            items[frontIndex] = item;
            backIndex = (backIndex + 1) % items.length;
            numItems += 1;
        }
        //if not empty or full, increment frontIndex, set item to the frontIndex's location, and increment numItems
        else{
            frontIndex = (frontIndex - 1) % items.length;
            //sets frontIndex to last index if frontIndex is 0 before decrementing
            if (frontIndex == -1){
                frontIndex = items.length - 1;
            }
            items[frontIndex] = item;
            numItems += 1;
        }
    }
    /** Removes the item from the front of this deque, and returns it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T removeFront(){
        T tempItem;
        // if array is empty, throw exception
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }
        //if array only has oneitem, item is stored in tempItem, set frontIndex to null, increment frontIndex so that the array is now empty,
        // and decrement numItems
        else if (numItems == 1){
            tempItem = items[frontIndex];
            items[frontIndex] = null;
            frontIndex = (frontIndex + 1) % items.length;
            numItems -= 1;
        }
        // if not empty or size 1, item is stored in tempItem, increment frontIndex, and and decrement numItems
        else{
            tempItem = items[frontIndex];
            frontIndex = (frontIndex + 1) % items.length;
            numItems -= 1;
        }
        return tempItem;
    }
    /** Adds an item to the back of this deque
     * @param item The item to add.
     */
    public void addBack(T item){
        // if array is full, resize, increment backIndex, set item to backIndex's location, and increment numitems
        if (this.isFull()){
            this.resize();
            backIndex = (backIndex + 1) % items.length;
            items[backIndex] = item;
            numItems += 1;
        }
        // if empty, set item to back Index, decrement frontIndex, and increment numItems
        else if (this.isEmpty()){
            items[backIndex] = item;
            frontIndex = (frontIndex - 1) % items.length;
            //sets frontIndex to last index if frontIndex is 0 before decrementing
            if (frontIndex == -1){
                frontIndex = items.length - 1;
            }
            numItems += 1;
        }
        else{
            backIndex = (backIndex + 1) % items.length;
            items[backIndex] = item;
            numItems += 1;
        }
    }
    /** Removes the item from the back of this deque, and returns it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T removeBack(){
        T tempItem;
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }
        else if (numItems == 1){
            tempItem = items[backIndex];
            items[backIndex] = null;
            backIndex = (backIndex - 1) % items.length;
            if (backIndex == -1){
                backIndex = items.length - 1;
            }
            numItems -= 1;
        }
        else{
            tempItem = items[backIndex];
            backIndex = (backIndex - 1) % items.length;
            if (backIndex == -1){
                backIndex = items.length - 1;
            }
            numItems -= 1;
        }
        return tempItem;
    }
    /** Returns the item at the front of the deque, without removing it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T peekFront(){
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }
        else{
            return items[frontIndex];
        }
    }
    /** Returns the item at the front of the deque, without removing it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T peekBack(){
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }
        else{
            return items[backIndex];
        }
    }

    /** Returns true if the deque is empty. Does this by checking if frontIndex is one more than backIndex */
    public boolean isEmpty(){
        if(frontIndex == (backIndex + 1) % items.length){
            return true;
        }
        return false;
    }
    /** Returns true if list is full. Does this by checking if frontIndex is two more than backIndex */
    public boolean isFull(){
        if(frontIndex == (backIndex + 2) % items.length){
            return true;
        }
        return false;
    }
    /** Removes all items from the deque. */
    public void clear(){
        //put backIndex one index behind frontIndex
        backIndex = (frontIndex - 1) % items.length;
        if (backIndex == -1){
            backIndex = items.length - 1;
        }
        //set items[backIndex] and items[frontIndex] to null;
        items[backIndex] = null;
        items[frontIndex] = null;
    }
    public void resize(){
        //make new array that is double the current size
        @SuppressWarnings("unchecked")
        T[] tmp = (T[]) new Object[2 * items.length];
        //copy items over
        int i = 0;
        while (frontIndex != backIndex){
            tmp[i] = items[frontIndex];
            frontIndex = (frontIndex + 1) % items.length;
            i += 1;
        }
        //frontIndex and backIndex are now equal and point to the last value to be copied over
        tmp[i] = items[frontIndex];
        //assign new array to instance variable of object and new front and back indexes
        items = tmp;
        frontIndex = 0;
        backIndex = i;
    }
}
