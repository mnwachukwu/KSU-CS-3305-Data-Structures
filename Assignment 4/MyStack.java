// Name:        Matt Nwachukwu
// Class        CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell Bradley
// Assignment:  4
// IDE Name:    VS Code

public class MyStack<T> {
    public Node<T> head; // store the head node of the structure

    // add a new item to the beginning of the structure
    public void push(T data) {
        Node<T> newNode = new Node<T>(data);

        // shifts everything down and adds the new data as the new head
        newNode.next = head;
        head = newNode;
    }

    // remove the first item from the structure and return it
    public T pop() {
        if (head == null) { // empty list
            throw new UnsupportedOperationException();
        }

        // store the current node
        Node<T> current = head;

        // advance head pointer
        head = head.next;

        // return the previous head (current)
        return current.data;
    }

    // return the top item from the stack (don't remove it)
    public T top() {
        if (head == null) { // empty list
            throw new UnsupportedOperationException();
        }

        return head.data;
    }

    // returns the size of the stack by counting every node
    public int size() {
        int stackSize = 0;

        Node<T> current = head;

        // seek the entire stack and keep a running count
        while (current != null) {
            stackSize++;
            current = current.next;
        }

        return stackSize;
    }

    // returns a boolean based on the size of the list
    public boolean isEmpty() {
        return size() == 0;
    }

    // clears a stack by dereferencing the entire chain
    public void clear() {
        head = null;
    }

    // prints the contents of a stack to a string list
    public String toString() {
        if (head == null) {
            return "";
        }

        Node<T> current = head;
        String contents = "";

        while (current != null) {
            contents += current.data + " ";
            current = current.next;
        }

        return contents;
    }

    // class to create nodes as objects
    private class Node<T2> {
        private T2 data; // data field
        private Node<T2> next; // link field

        public Node(T2 item) // constructor method
        {
            data = item;
            next = null;
        }
    }
}
