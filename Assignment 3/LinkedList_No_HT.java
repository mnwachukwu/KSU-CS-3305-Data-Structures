// Name:        Matt Nwachukwu
// Class        CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell
// Assignment:  3
// IDE Name:    VS Code

/*
  This class define a linked list that stores integer values.
  The class does NOT use Head and Tail pointer as the textbook class deos.
*/

public class LinkedList_No_HT {
   public Node head;

   // constructor method to create a list object with an initial node.
   public LinkedList_No_HT() {
      head = null;
   }

   // method add node to end of list
   public void addLastNode(int data) {
      if (head == null)
         head = new Node(data); // one node list
      else {
         Node temp = head;
         while (temp.next != null) {
            temp = temp.next;
         }

         temp.next = new Node(data); // link new node as last node
      }
   }

   // ======== Your part to complete for this assignment =========

   // method #1: add first node
   public void addFirstNode(int data) {
      Node newNode = new Node(data);

      // shifts everything down and adds the new data as the new head
      newNode.next = head;
      head = newNode;
   }

   // method #2: add node at index
   public void addAtIndex(int index, int data) {
      if (index < 0) { // index is out of range
         throw new IndexOutOfBoundsException();
      }

      if (index == 0) { // index is the first node, logic already exists
         addFirstNode(data);
         return;
      }

      Node current = head;
      int count = 0;

      // seek to the index node
      while (current != null && count < index - 1) {
         current = current.next;
         count++;
      }

      if (current == null) { // means that the index is out of upper bounds
         throw new IndexOutOfBoundsException();
      }

      // all is well
      // add the node here and shift the reference of the sought head to next
      Node newNode = new Node(data);
      newNode.next = current.next;
      current.next = newNode;
   }

   // method #3: remove first node
   public void removeFirstNode() {
      if (head == null) { // empty list
         throw new UnsupportedOperationException();
      }

      // advance head pointer
      head = head.next;
   }

   // method #4: remove last node
   public void removeLastNode() {
      if (head == null) { // empty list
         throw new UnsupportedOperationException();
      }

      if (head.next == null) { // single item list
         head = null;
         return;
      }

      Node current = head;

      while (current.next.next != null) { // seek the next to last node
         current = current.next;
      }

      current.next = null; // removes the last node
   }

   // method #5: remove node at index
   public void removeAtIndex(int index) {
      if (index < 0) { // out of bounds
         throw new IndexOutOfBoundsException();
      }

      if (head == null) { // empty list
         throw new UnsupportedOperationException();
      }

      if (index == 0) { // logic for removing first node already exists
         removeFirstNode();
         return;
      }

      Node current = head;
      int count = 0;

      while (current.next != null && count < index - 1) { // seek the index node
         current = current.next;
         count++;
      }

      if (current.next == null) { // indicates the index is out of upper bounds
         throw new IndexOutOfBoundsException();
      }

      current.next = current.next.next; // remove it by linking the current to the next node from the removed node
   }

   // method #6: countNodes
   public int countNodes() {
      int listSize = 0;

      Node current = head;

      // seek the entire list and keep a running count
      while (current != null) {
         listSize++;
         current = current.next;
      }

      return listSize;
   }

   // method #7: pritnInReverse (must be a Recursive method)
   public void printInReverseRecursive(Node L) {
      if (L == null) { // exit condition; no more list to print
         return;
      }

      printInReverseRecursive(L.next);
      System.out.print(L.data + "   ");
   }

   // ================= end of your part ==============

   // method to print out the list
   public void printList() {
      Node temp;
      temp = head;
      while (temp != null) {
         System.out.print(temp.data + "   ");
         temp = temp.next;
      }
   }

   // class to create nodes as objects
   private class Node {
      private int data; // data field
      private Node next; // link field

      public Node(int item) // constructor method
      {
         data = item;
         next = null;
      }
   }
}
