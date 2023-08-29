
import java.util.ArrayList;

public class DLStack<T> implements DLStackADT<T> {
	// top stack
    private DoubleLinkedNode<T> top;
    //variable for number of items n stack
    private int numItems;

    	//constructor
    public DLStack() {
        top = null;
        numItems = 0;
    }
    
    // method to add an item to the top of the stack
    public void push(T dataItem) {
        DoubleLinkedNode<T> newNode = new DoubleLinkedNode<T>(dataItem);
        newNode.setNext(top);
        if (top != null) {
            top.setPrevious(newNode);
        }
        top = newNode;
        numItems++;
    }
    
    // method to remove and return the top item of the stack
    public T pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty.");
        }
        T item = top.getElement();
        top = top.getNext();
        if (top != null) {
            top.setPrevious(null);
        }
        numItems--;
        return item;
    }
    
    // method to remove and return the kth item from the top of the stack
    public T pop(int k) throws InvalidItemException {
        int counter = 0;
        if(k<=0 || k > numItems) {
            throw new InvalidItemException("Item is invalid");
        }
        DoubleLinkedNode<T> currNode = top;
        counter++;//1
        while(counter != k) {
            counter++;
            currNode = currNode.getNext();
        }
        DoubleLinkedNode<T> prevNode = currNode.getPrevious();
        DoubleLinkedNode<T> nextNode = currNode.getNext();
        prevNode.setNext(nextNode);
        nextNode.setPrevious(prevNode);
        return currNode.getElement();
    }
    
    // method to return the top item of the stack without removing it
    public T peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty.");
        }
        return top.getElement();
    }
    
    // method to check if the stack is empty
    public boolean isEmpty() {
        return (numItems == 0);
    }
    
    // method to return the top node of the stack
    public int size() {
        return numItems;
    }
    
    // method to return the top node of the stack
    public DoubleLinkedNode<T> getTop() {
        return top;
    }
    
    // method to return a string representation of the stack
    public String toString() {
        String stackInString = "[";
        T[] stackArr = this.stackToArray();

        for(int i=0;i<stackArr.length;i++) {
            if(i+1==stackArr.length) {
                stackInString += stackArr[i].toString() + "]";
            }
            stackInString += stackArr[i].toString() + ", ";
        }
        return stackInString;
    }
    
    // helper method that converts the stack to an array
    private T[] stackToArray() {
        int counter = 0;
        ArrayList<T> arrList = new ArrayList<T>();
        if(numItems == 0) {
            return (T[]) arrList.toArray();
        }
        counter++;
        DoubleLinkedNode<T> currNode = top;
        // Start from the top of the stack
        arrList.add(currNode.getElement());
        counter++;
        while (currNode != null) {
            arrList.add(currNode.getElement());
            currNode = currNode.getNext();
            counter++;
        }
        // Converts the ArrayList to an array and returns it
        return (T[]) arrList.toArray();
    }
}
