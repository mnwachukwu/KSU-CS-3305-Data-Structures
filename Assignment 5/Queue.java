// Name:        Matt Nwachukwu
// Class:       CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell
// Assignment:  5
// IDE Name:    VS Code

/*
   This class defines a generic Queue ADT implemented using a linked-list.
   The class supports standard queue operations: enqueue, dequeue,
   front, size, isEmpty, and print.
*/
public class Queue<T> {
   private Node head; // points to the front of the queue (dequeue end)
   private Node tail; // points to the rear of the queue (enqueue end)
   private int size; // tracks the number of elements currently in the queue

   // Ctor
   public Queue() {
      head = null;
      tail = null;
      size = 0;
   }

   // Adds a new element to the rear of the queue.
   public void enqueue(T e) {
      Node newNode = new Node(e);

      if (tail == null) {
         // queue was empty; new node is both head and tail
         head = newNode;
         tail = newNode;
      } else {
         // attach new node after current tail and advance tail pointer
         tail.next = newNode;
         tail = newNode;
      }

      size++; // increment element count
   }

   // Removes and returns the element at the front of the queue.
   public T dequeue() {
      if (head == null) {
         throw new UnsupportedOperationException("Cannot dequeue from an empty queue.");
      }

      T removedData = head.data; // save front data to return
      head = head.next; // advance head pointer (removes front node)

      if (head == null) {
         // queue is now empty; reset tail as well
         tail = null;
      }

      size--; // decrement element count
      return removedData;
   }

   // Returns (without removing) the element at the front of the queue.
   public T front() {
      if (head == null) {
         throw new UnsupportedOperationException("Queue is empty.");
      }

      return head.data; // return front data without modifying the queue
   }

   // Returns the number of elements currently in the queue
   public int size() {
      return size;
   }

   // Returns true if the queue contains no elements, false otherwise
   public boolean isEmpty() {
      return head == null; // if head is null, the list has no nodes
   }

   // Prints all elements in the queue from front to rear, separated by spaces.
   // Does not modify the queue.
   public void printQueue() {
      Node temp = head; // start at the front

      while (temp != null) {
         System.out.print(temp.data + "   ");
         temp = temp.next;
      }

      System.out.println(); // newline after all elements are printed
   }

   // Node is the building block of the linked-list backing this queue.
   // Each node holds a data element and a reference to the next node.
   private class Node {
      private T data; // data field (generic type)
      private Node next; // link to next node

      // constructor: creates a node with given data and a null link
      public Node(T item) {
         data = item;
         next = null;
      }
   }
}
