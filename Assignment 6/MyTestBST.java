// Name:        Matt Nwachukwu
// Class:       CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Bradley
// Assignment:  6
// IDE Name:    VS Code

/*
  This program provides an interactive, menu-driven interface for a Binary
  Search Tree (BST). The user must first select a data type (option 0) before
  any tree operation is available. All insert and delete operations display
  the inorder state of the tree both before and after the operation.
*/

import java.util.Scanner;
import java.util.ArrayList;

public class MyTestBST {

   // BST instances for each supported data type.
   private static BST<String> stringTree = new BST<>();
   private static BST<Integer> intTree = new BST<>();

   // Holds the user-selected type: "string" or "integer". Null until option 0
   // runs.
   private static String treeType = null;

   // ====================== traversal helpers ======================

   /*
    * Builds a space-separated inorder string of all tree elements using the
    * BST iterator, which performs an inorder traversal internally.
    * Returns "Empty Tree" when the tree has no nodes.
    */
   private static <E extends Comparable<E>> String buildInorder(BST<E> tree) {
      if (tree.isEmpty()) {
         return "Empty Tree";
      }

      StringBuilder sb = new StringBuilder();

      for (E element : tree) {
         sb.append(element).append(" ");
      }

      return sb.toString().trim();
   }

   /*
    * Builds a space-separated preorder string by delegating to a recursive helper.
    * Returns "Empty Tree" when the tree has no nodes.
    */
   private static <E extends Comparable<E>> String buildPreorder(BST<E> tree) {
      if (tree.isEmpty()) {
         return "Empty Tree";
      }

      StringBuilder sb = new StringBuilder();
      preorderHelper(tree.getRoot(), sb);

      return sb.toString().trim();
   }

   /*
    * Recursive preorder helper: visits root, then left subtree, then right
    * subtree.
    */
   private static <E extends Comparable<E>> void preorderHelper(
         BST.TreeNode<E> node, StringBuilder sb) {

      if (node == null) {
         return;
      }

      sb.append(node.element).append(" "); // visit root first
      preorderHelper(node.left, sb); // then left
      preorderHelper(node.right, sb); // then right
   }

   /*
    * Builds a space-separated postorder string by delegating to a recursive
    * helper.
    * Returns "Empty Tree" when the tree has no nodes.
    */
   private static <E extends Comparable<E>> String buildPostorder(BST<E> tree) {
      if (tree.isEmpty()) {
         return "Empty Tree";
      }

      StringBuilder sb = new StringBuilder();
      postorderHelper(tree.getRoot(), sb);

      return sb.toString().trim();
   }

   /*
    * Recursive postorder helper: visits left subtree, then right subtree, then
    * root.
    */
   private static <E extends Comparable<E>> void postorderHelper(
         BST.TreeNode<E> node, StringBuilder sb) {

      if (node == null) {
         return;
      }

      postorderHelper(node.left, sb); // left first
      postorderHelper(node.right, sb); // then right
      sb.append(node.element).append(" "); // visit root last
   }

   // ====================== menu display ======================

   /*
    * Prints the full main menu to standard output.
    */
   private static void displayMenu() {
      System.out.println("\n--------------MAIN MENU--------------");
      System.out.println("0.  Enter Tree Data Type (integer or string)");
      System.out.println("1.  Insert Data Element");
      System.out.println("2.  Delete Data Element");
      System.out.println("3.  Search for Data Element");
      System.out.println("4.  Print Tree Size");
      System.out.println("5.  Path from Root to Data Element");
      System.out.println("6.  Check if Empty Tree");
      System.out.println("7.  Print Preorder Traversal");
      System.out.println("8.  Print Inorder Traversal");
      System.out.println("9.  Print Postorder Traversal");
      System.out.println("10. Exit program");
      System.out.print("Enter option number: ");
   }

   // ====================== entry point ======================

   /*
    * Entry point. Displays the menu, reads user input, and dispatches to the
    * appropriate option handler inside a sentinel loop. The loop exits only
    * when the user selects option 10.
    */
   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);

      displayMenu();

      while (true) {

         // Reject non-integer input before even attempting to read.
         while (!scanner.hasNextInt()) {
            System.out.println("\nInvalid input. Please enter a number (0-10).");
            scanner.nextLine();
            displayMenu();
         }

         int option = scanner.nextInt();
         scanner.nextLine(); // consume the trailing newline left by nextInt

         // Guard: all tree operations require a type selection first.
         if (option != 0 && option != 10 && treeType == null) {
            System.out.println("\nNo tree type selected. Please run Option 0 first.");
            displayMenu();
            continue;
         }

         // Dispatch to the correct handler.
         if (option == 0) {
            handleSetType(scanner);
         } else if (option == 1) {
            handleInsert(scanner);
         } else if (option == 2) {
            handleDelete(scanner);
         } else if (option == 3) {
            handleSearch(scanner);
         } else if (option == 4) {
            handleSize();
         } else if (option == 5) {
            handlePath(scanner);
         } else if (option == 6) {
            handleIsEmpty();
         } else if (option == 7) {
            handlePreorder();
         } else if (option == 8) {
            handleInorder();
         } else if (option == 9) {
            handlePostorder();
         } else if (option == 10) {
            System.out.println("\nExiting program.");
            break;
         } else {
            System.out.println("\nInvalid option. Please enter a number between 0 and 10.");
         }

         displayMenu();
      }

      scanner.close();
   }

   // ====================== option handlers ======================

   /*
    * Option 0: Prompts the user to select "integer" or "string" as the tree data
    * type. Resets both BST instances whenever a new type is chosen.
    */
   private static void handleSetType(Scanner scanner) {
      System.out.print("\nEnter tree data type (integer or string): ");
      String type = scanner.nextLine().trim().toLowerCase();

      // Repeat until a valid type is provided.
      while (!type.equals("integer") && !type.equals("string")) {
         System.out.print("Invalid type. Please enter 'integer' or 'string': ");
         type = scanner.nextLine().trim().toLowerCase();
      }

      treeType = type;
      stringTree = new BST<>(); // reset; the user is starting fresh with a new type
      intTree = new BST<>();

      System.out.println("\nTree type set to: " + treeType);
   }

   /*
    * Option 1: Reads an element from the user and inserts it into the active BST.
    * Prints the inorder state of the tree both before and after the insertion.
    */
   private static void handleInsert(Scanner scanner) {
      System.out.print("\nEnter element to insert: ");
      String input = scanner.nextLine().trim();

      if (treeType.equals("string")) {
         System.out.println("\nBST before inserting " + input + " (Inorder): " + buildInorder(stringTree));
         boolean inserted = stringTree.insert(input);
         System.out.println("BST after inserting " + input + " (Inorder): " + buildInorder(stringTree));

         if (!inserted) {
            System.out.println("Note: \"" + input + "\" already exists; duplicate was not inserted.");
         }
      } else {
         // Validate that the input parses as an integer.
         try {
            int value = Integer.parseInt(input);

            System.out.println("\nBST before inserting " + value + " (Inorder): " + buildInorder(intTree));
            boolean inserted = intTree.insert(value);
            System.out.println("BST after inserting " + value + " (Inorder): " + buildInorder(intTree));

            if (!inserted) {
               System.out.println("Note: " + value + " already exists; duplicate was not inserted.");
            }
         } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter an integer value.");
         }
      }
   }

   /*
    * Option 2: Reads an element from the user and removes it from the active BST.
    * Prints the inorder state of the tree both before and after the deletion.
    * Prints "Empty Tree" and returns early if the tree has no nodes.
    */
   private static void handleDelete(Scanner scanner) {
      if (isActiveTreeEmpty()) {
         System.out.println("\nEmpty Tree");
         return;
      }

      System.out.print("\nEnter element to delete: ");
      String input = scanner.nextLine().trim();

      if (treeType.equals("string")) {
         System.out.println("\nBST before deleting " + input + " (Inorder): " + buildInorder(stringTree));
         boolean deleted = stringTree.delete(input);
         System.out.println("BST after deleting " + input + " (Inorder): " + buildInorder(stringTree));

         if (!deleted) {
            System.out.println("Note: \"" + input + "\" was not found in the tree.");
         }
      } else {
         try {
            int value = Integer.parseInt(input);

            System.out.println("\nBST before deleting " + value + " (Inorder): " + buildInorder(intTree));
            boolean deleted = intTree.delete(value);
            System.out.println("BST after deleting " + value + " (Inorder): " + buildInorder(intTree));

            if (!deleted) {
               System.out.println("Note: " + value + " was not found in the tree.");
            }
         } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter an integer value.");
         }
      }
   }

   /*
    * Option 3: Reads an element and prints whether it exists in the active BST.
    * Prints "Empty Tree" and returns early if the tree has no nodes.
    */
   private static void handleSearch(Scanner scanner) {
      if (isActiveTreeEmpty()) {
         System.out.println("\nEmpty Tree");
         return;
      }

      System.out.print("\nEnter element to search for: ");
      String input = scanner.nextLine().trim();

      if (treeType.equals("string")) {
         System.out.println("\nIs " + input + " in the tree? " + stringTree.search(input));
      } else {
         try {
            int value = Integer.parseInt(input);
            System.out.println("\nIs " + value + " in the tree? " + intTree.search(value));
         } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter an integer value.");
         }
      }
   }

   /*
    * Option 4: Prints the total number of nodes in the active BST.
    * Prints "Empty Tree" and returns early if the tree has no nodes.
    */
   private static void handleSize() {
      if (isActiveTreeEmpty()) {
         System.out.println("\nEmpty Tree");
         return;
      }

      int size = treeType.equals("string") ? stringTree.getSize() : intTree.getSize();
      System.out.println("\nTree size: " + size);
   }

   /*
    * Option 5: Reads an element and prints every node on the path from the BST
    * root down to that element. Prints "Empty Tree" and returns early if the
    * tree is empty; prints a "not found" message if the element does not exist.
    */
   private static void handlePath(Scanner scanner) {
      if (isActiveTreeEmpty()) {
         System.out.println("\nEmpty Tree");
         return;
      }

      System.out.print("\nEnter element to find path to: ");
      String input = scanner.nextLine().trim();

      if (treeType.equals("string")) {
         ArrayList<BST.TreeNode<String>> path = stringTree.path(input);

         // The path ends at the target only when the element was actually found.
         if (path.isEmpty() || !path.get(path.size() - 1).element.equals(input)) {
            System.out.println("\n\"" + input + "\" was not found in the tree.");
            return;
         }

         // Build the path string from the returned node list.
         StringBuilder pathStr = new StringBuilder();
         for (BST.TreeNode<String> node : path) {
            if (pathStr.length() > 0)
               pathStr.append(" ");
            pathStr.append(node.element);
         }
         System.out.println("\nPath from root to " + input + ": " + pathStr);

      } else {
         try {
            int value = Integer.parseInt(input);
            ArrayList<BST.TreeNode<Integer>> path = intTree.path(value);

            if (path.isEmpty() || !path.get(path.size() - 1).element.equals(value)) {
               System.out.println("\n" + value + " was not found in the tree.");
               return;
            }

            StringBuilder pathStr = new StringBuilder();
            for (BST.TreeNode<Integer> node : path) {
               if (pathStr.length() > 0)
                  pathStr.append(" ");
               pathStr.append(node.element);
            }
            System.out.println("\nPath from root to " + value + ": " + pathStr);

         } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter an integer value.");
         }
      }
   }

   /*
    * Option 6: Prints whether the active BST is currently empty.
    */
   private static void handleIsEmpty() {
      boolean empty = treeType.equals("string") ? stringTree.isEmpty() : intTree.isEmpty();
      System.out.println("\nIs empty tree? " + empty);
   }

   /*
    * Option 7: Prints the preorder traversal of the active BST.
    * Prints "Empty Tree" and returns early if the tree has no nodes.
    */
   private static void handlePreorder() {
      if (isActiveTreeEmpty()) {
         System.out.println("\nEmpty Tree");
         return;
      }

      String result = treeType.equals("string")
            ? buildPreorder(stringTree)
            : buildPreorder(intTree);

      System.out.println("\nPreorder: " + result);
   }

   /*
    * Option 8: Prints the inorder traversal of the active BST.
    * Prints "Empty Tree" and returns early if the tree has no nodes.
    */
   private static void handleInorder() {
      if (isActiveTreeEmpty()) {
         System.out.println("\nEmpty Tree");
         return;
      }

      String result = treeType.equals("string")
            ? buildInorder(stringTree)
            : buildInorder(intTree);

      System.out.println("\nInorder: " + result);
   }

   /*
    * Option 9: Prints the postorder traversal of the active BST.
    * Prints "Empty Tree" and returns early if the tree has no nodes.
    */
   private static void handlePostorder() {
      if (isActiveTreeEmpty()) {
         System.out.println("\nEmpty Tree");
         return;
      }

      String result = treeType.equals("string")
            ? buildPostorder(stringTree)
            : buildPostorder(intTree);

      System.out.println("\nPostorder: " + result);
   }

   // ====================== utility ======================

   /*
    * Returns true if the currently selected tree (string or integer) is empty.
    * Relies on treeType being non-null; callers must have already confirmed
    * treeType is set before calling this method.
    */
   private static boolean isActiveTreeEmpty() {
      return treeType.equals("string") ? stringTree.isEmpty() : intTree.isEmpty();
   }
}
