import java.util.*;

public class CallStack extends CircularArrayDequeImplementation<Method>{
      private Method[] items;
      private int frontIndex;
      private int backIndex;
      private int numItems;
      private SimulationComponent component;
      public static final int DEFAULT_SIZE = 10;

      public CallStack(SimulationComponent component){
          this.component = component;
          Method[] tmp = new Method[DEFAULT_SIZE + 1];
          items = tmp;
          numItems = 0;
          frontIndex = 0;
          backIndex = DEFAULT_SIZE;
      }
      /** Adds an item to the front of this deque
       * @param item The item to add.
       */
      public void addFront(Method method){
          //if full, resize the array, add item, and increment numItems
          if (this.isFull()){
              this.resize();
              frontIndex = items.length - 1;
              items[frontIndex] = method;
              numItems += 1;
          }
          //if empty, assign item to frontIndex, make both indexes refer to same item, and increment numItems
          else if (this.isEmpty()){
              items[frontIndex] = method;
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
              items[frontIndex] = method;
              numItems += 1;
          }
          component.addMethodToGraphicalStack(method);
      }
      /** Removes the item from the front of this deque, and returns it.
       * Throws a NoSuchElementException if the deque is empty
       * @return the item at the top of the deque
       */
      public Method removeFront(){
           Method tempItem = null;
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
           component.removeMethodFromGraphicalStack(tempItem);
           return tempItem;
      }
      /** Adds an item to the back of this deque
       * @param item The item to add.
       */
      public void addBack(Method method){
          throw new UnsupportedOperationException("This operation is not supported by a stack.");
      }

      /** Removes the item from the back of this deque, and returns it.
       * Throws a NoSuchElementException if the deque is empty
       * @return the item at the top of the deque
       */
      public Method removeBack(){
          throw new UnsupportedOperationException("This operation is not supported by a stack.");
      }

      /** Returns the item at the front of the deque, without removing it.
       * Throws a NoSuchElementException if the deque is empty
       * @return the item at the top of the deque
       */
      public Method peekFront(){
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
      public Method peekBack(){
          throw new UnsupportedOperationException("This operation is not supported by a stack.");
      }
}
