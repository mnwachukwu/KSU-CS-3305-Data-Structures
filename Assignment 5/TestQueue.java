// Name:        Matt Nwachukwu
// Class:       CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell
// Assignment:  5
// IDE Name:    VS Code

import java.util.Scanner;

/*
   This program provides an interactive, menu-driven interface that allows
   the user to test all operations of the generic Queue class. The queue
   stores integer values for this test. The program starts with an empty
   queue and continues running until the user selects option 7 (Exit).
*/
public class TestQueue {
   public static void main(String[] args) {

      Scanner input = new Scanner(System.in);

      // create an empty integer queue at program start
      Queue<Integer> queue = new Queue<>();

      int option = 0; // stores the user's menu selection

      // keep running until the user chooses to exit
      do {
         printMenu(); // display the menu before every prompt
         System.out.print("Enter option number: ");
         option = input.nextInt();

         System.out.println(); // blank line after input for readability

         switch (option) {

            case 1: // Enqueue element
               System.out.print("Enter integer value to enqueue: ");
               int enqueueValue = input.nextInt();
               queue.enqueue(enqueueValue);
               System.out.println("Value " + enqueueValue + " has been added to the queue.");
               break;

            case 2: // Dequeue element
               if (queue.isEmpty()) {
                  System.out.println("Empty Queue -- cannot dequeue.");
               } else {
                  int dequeued = queue.dequeue();
                  System.out.println("Dequeued value: " + dequeued);
               }
               break;

            case 3: // Get front element
               if (queue.isEmpty()) {
                  System.out.println("Empty Queue -- no front element.");
               } else {
                  System.out.println("Front element: " + queue.front());
               }
               break;

            case 4: // Get queue size
               if (queue.isEmpty()) {
                  System.out.println("Empty Queue -- size is 0.");
               } else {
                  System.out.println("Queue size: " + queue.size());
               }
               break;

            case 5: // Is Empty queue?
               if (queue.isEmpty()) {
                  System.out.println("Queue is EMPTY.");
               } else {
                  System.out.println("Queue is NOT empty.  (Size: " + queue.size() + ")");
               }
               break;

            case 6: // Print queue
               if (queue.isEmpty()) {
                  System.out.println("Empty Queue -- nothing to print.");
               } else {
                  System.out.print("Queue contents (front to rear): ");
                  queue.printQueue();
               }
               break;

            case 7: // Exit program
               System.out.println("Exiting program.");
               break;

            default: // invalid option
               System.out.println("Invalid option. Please enter a number between 1 and 7.");
               break;
         }

         System.out.println(); // blank line after output for readability

      } while (option != 7); // loop until user exits

      input.close(); // close scanner before exiting

   }

   // displays the main menu to the user.
   public static void printMenu() {
      System.out.println();
      System.out.println("--------MAIN MENU--------");
      System.out.println("1 - Enqueue element");
      System.out.println("2 - Dequeue element");
      System.out.println("3 - Get front element");
      System.out.println("4 - Get queue size");
      System.out.println("5 - Is Empty queue?");
      System.out.println("6 - Print queue");
      System.out.println("7 - Exit program");
      System.out.println();
   }
}
