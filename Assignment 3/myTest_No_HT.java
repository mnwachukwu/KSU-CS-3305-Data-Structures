// Name:        Matt Nwachukwu
// Class        CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell Bradley
// Assignment:  3
// IDE Name:    VS Code

import java.util.Scanner;

public class myTest_No_HT {
   public static void main(String[] args) {
      LinkedList_No_HT myList = new LinkedList_No_HT(); // create a list object
      Scanner scanner = null; // input reader
      int input = 0; // menu input tracker
      int nodeInput = 0; // list input tracker
      int indexInput = 0; // index input tracker

      // keep going while input is not 10 (exit condition)
      while (input != 10) {
         // init
         scanner = new Scanner(System.in);
         printMainMenu();
         input = scanner.nextInt();

         // read input, act accordingly with output
         switch (input) {
            case 1: // add first node
               System.out.print("Add First Node. Enter a Number: ");

               scanner = new Scanner(System.in);
               nodeInput = scanner.nextInt();

               System.out.print("List contents before adding " + nodeInput + " is:\t");
               myList.printList();

               myList.addFirstNode(nodeInput);

               System.out.print("\nList contents after adding " + nodeInput + " is:\t");
               myList.printList();
               break;

            case 2: // add last node
               System.out.print("Add Last Node. Enter a Number: ");

               scanner = new Scanner(System.in);
               nodeInput = scanner.nextInt();

               System.out.print("List contents before adding " + nodeInput + " is:\t");
               myList.printList();

               myList.addLastNode(nodeInput);

               System.out.print("\nList contents after adding " + nodeInput + " is:\t");
               myList.printList();
               break;

            case 3: // add at index
               System.out.print("Add At Index. Enter a Number: ");

               scanner = new Scanner(System.in);
               nodeInput = scanner.nextInt();

               System.out.print("Add At Index. " + nodeInput + " entered. Enter the Index: ");

               indexInput = scanner.nextInt();

               System.out.print("List contents before adding " + nodeInput + " at " + indexInput + " is:\t");
               myList.printList();

               try {
                  myList.addAtIndex(indexInput, nodeInput);
               } catch (IndexOutOfBoundsException e) {
                  System.out.println("\nInvalid index, try again");
                  continue;
               }

               System.out.print("\nList contents after adding " + nodeInput + " at " + indexInput + " is:\t");
               myList.printList();
               break;

            case 4: // remove first node
               System.out.println("Remove First Node.");

               System.out.print("List contents before removing first node is:\t");
               myList.printList();

               try {
                  myList.removeFirstNode();
               } catch (UnsupportedOperationException e) {
                  System.out.println("\nList is Empty");
                  continue;
               }

               System.out.print("\nList contents after removing first node is:\t");
               myList.printList();
               break;

            case 5: // remove last node
               System.out.println("Remove Last Node.");

               System.out.print("List contents before removing last node is:\t");
               myList.printList();

               try {
                  myList.removeLastNode();
               } catch (UnsupportedOperationException e) {
                  System.out.println("\nList is Empty");
                  continue;
               }

               System.out.print("\nList contents after removing last node is:\t");
               myList.printList();
               break;

            case 6: // remove at index
               System.out.print("Remove At Index. Enter the Index: ");

               scanner = new Scanner(System.in);
               indexInput = scanner.nextInt();

               System.out.print("List contents before removing node at " + indexInput + " is:\t");
               myList.printList();

               try {
                  myList.removeAtIndex(indexInput);
               } catch (IndexOutOfBoundsException e) {
                  System.out.println("\nInvalid index, try again");
                  continue;
               } catch (UnsupportedOperationException e) {
                  System.out.println("\nList is Empty");
                  continue;
               }

               System.out.print("\nList contents after removing node at " + indexInput + " is:\t");
               myList.printList();
               break;

            case 7: // print the list's current size
               System.out.println("The list's current size is: " + myList.countNodes());
               break;

            case 8: // print the current list
               System.out.print("The current list: ");
               myList.printList();

               if (myList.countNodes() <= 0) {
                  System.out.println("The list is empty.");
               }

               System.out.println();
               break;

            case 9: // print the current list in reverse
               System.out.print("The current list, in reverse: ");
               myList.printInReverseRecursive(myList.head);

               if (myList.countNodes() <= 0) {
                  System.out.println("The list is empty.");
               }

               System.out.println();
               break;

            case 10: // exit
               continue;

            default: // invalid option
               System.out.println("Invalid option.");
               break;
         }
      }

      // clean up
      if (scanner != null) {
         scanner.close();
      }

      System.out.println("Program execution ended.");
   }

   // a method that handles printing the menu
   public static void printMainMenu() {
      System.out.println();
      System.out.println("---------- MAIN MENU ----------");
      System.out.println("1- Add First Node");
      System.out.println("2- Add Last Node");
      System.out.println("3- Add At Index");
      System.out.println("4- Remove First Node");
      System.out.println("5- Remove Last Node");
      System.out.println("6- Remove At Index");
      System.out.println("7- Print List Size");
      System.out.println("8- Print List Forward");
      System.out.println("9- Print List In Reverse");
      System.out.println("10- Exit Program");
      System.out.println();
      System.out.print("Enter option number: ");
   }
}
