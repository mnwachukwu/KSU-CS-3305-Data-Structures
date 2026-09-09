// Name:        Matt Nwachukwu
// Class:       CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Bradley
// Assignment:  6
// IDE Name:    VS Code

/*
  This program reads a line of text from the user, splits it on spaces to
  extract individual words, inserts those words into a BST (which silently
  discards duplicates), and then traverses the BST inorder to print the
  unique words in sorted order. A menu with a sentinel loop drives the
  interaction so the user can run multiple trials in one session.
*/

import java.util.Scanner;

public class RemoveDuplicates {

   // Stores the most recently entered line of text; null until option 1 runs.
   private static String inputText = null;

   // ====================== menu display ======================

   /*
    * Prints the main menu to standard output.
    */
   private static void displayMenu() {
      System.out.println("\n--------------MAIN MENU--------------");
      System.out.println("1. Read input string");
      System.out.println("2. Remove duplicates and display outputs");
      System.out.println("3. Exit program");
      System.out.print("Enter option number: ");
   }

   // ====================== entry point ======================

   /*
    * Entry point. Displays the menu and dispatches to option handlers inside a
    * sentinel loop. The loop exits only when the user selects option 3.
    */
   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);

      displayMenu();

      while (true) {

         // Reject non-integer input before attempting to read.
         while (!scanner.hasNextInt()) {
            System.out.println("\nInvalid input. Please enter a number (1-3).");
            scanner.nextLine();
            displayMenu();
         }

         int option = scanner.nextInt();
         scanner.nextLine(); // consume the trailing newline left by nextInt

         if (option == 1) {
            handleReadInput(scanner);
         } else if (option == 2) {
            handleRemoveDuplicates();
         } else if (option == 3) {
            System.out.println("\nExiting program.");
            break;
         } else {
            System.out.println("\nInvalid option. Please enter 1, 2, or 3.");
         }

         displayMenu();
      }

      scanner.close();
   }

   // ====================== option handlers ======================

   /*
    * Option 1: Reads one full line of text from the user and stores it for
    * later processing. Any previously stored text is replaced.
    */
   private static void handleReadInput(Scanner scanner) {
      System.out.print("\nEnter input string: ");
      inputText = scanner.nextLine();
      System.out.println("Input stored successfully.");
   }

   /*
    * Option 2: Processes the stored input text by splitting on single spaces,
    * inserting each token into a BST<String> (duplicates are automatically
    * rejected by BST.insert), then printing the original text and the inorder
    * traversal of the BST as the deduplicated, sorted result.
    */
   private static void handleRemoveDuplicates() {
      if (inputText == null) {
         System.out.println("\nNo input text available. Please use Option 1 first.");
         return;
      }

      // A fresh BST is created for each processing run so previous runs do not
      // carry over if the user enters a new string and processes it again.
      BST<String> wordTree = new BST<>();
      String[] tokens = inputText.split(" ");

      for (String token : tokens) {
         if (!token.isEmpty()) { // guard against runs of spaces producing empty tokens
            wordTree.insert(token);
         }
      }

      // Collect the inorder traversal into a single space-separated string.
      StringBuilder processed = new StringBuilder();
      for (String word : wordTree) {
         if (processed.length() > 0)
            processed.append(" ");
         processed.append(word);
      }

      System.out.println("\nOriginal Text:");
      System.out.println(inputText);
      System.out.println("\nProcessed Text:");
      System.out.println(processed.toString());
   }
}
