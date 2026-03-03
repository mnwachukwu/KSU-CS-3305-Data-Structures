// Name:        Matt Nwachukwu
// Class:       CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell
// Assignment:  5
// IDE Name:    VS Code

import java.util.Scanner;

// This program implements a Radix Sort algorithm using ten Queue objects
// (Q0 through Q9), one for each possible digit value (0-9).
public class RadixSort {
   static int[] inputs; // the one and only input array
   static int arraySize; // number of elements in the array
   static boolean arrayReady; // flag: true once the array has been filled

   // The ten queues required by Radix Sort (one per digit 0-9)
   static Queue<Integer>[] Q = new Queue[10];

   public static void main(String[] args) {

      Scanner input = new Scanner(System.in);

      // initialize the ten queue objects
      for (int i = 0; i < 10; i++) {
         Q[i] = new Queue<Integer>();
      }

      arrayReady = false; // no data has been entered yet

      int option = 0; // stores the user's menu choice

      // sentinel loop: keep running until the user selects option 4 (Exit)
      do {
         printMenu();
         System.out.print("Enter option number: ");
         option = input.nextInt();

         System.out.println(); // blank line after input for readability

         switch (option) {

            case 1: // Read array size
               System.out.print("Enter the number of input values (array size): ");
               arraySize = input.nextInt();

               // validate: array must hold at least one element
               if (arraySize <= 0) {
                  System.out.println("Array size must be a positive integer. Please try again.");
               } else {
                  inputs = new int[arraySize]; // allocate the input array
                  arrayReady = false; // reset ready flag until values are entered
                  System.out.println("Array size set to " + arraySize + ".");
               }
               break;

            case 2: // Read array values
               // make sure the user has set the array size first
               if (inputs == null) {
                  System.out.println("Please set the array size first (Option 1).");
                  break;
               }

               System.out.println("Enter " + arraySize + " positive integer values:");
               for (int i = 0; i < arraySize; i++) {
                  System.out.print("  Value [" + (i + 1) + "]: ");
                  int val = input.nextInt();

                  // reject negative inputs as required by the assignment
                  while (val < 0) {
                     System.out.println("  Negative values are not allowed. Please re-enter.");
                     System.out.print("  Value [" + (i + 1) + "]: ");
                     val = input.nextInt();
                  }

                  inputs[i] = val; // store the validated value
               }

               arrayReady = true; // array is now populated and ready to sort
               System.out.println("Array values have been stored successfully.");
               break;

            case 3: // Run Radix Sort and print outputs
               if (!arrayReady) {
                  System.out.println("Please enter array values first (Options 1 and 2).");
                  break;
               }

               // build a comma-separated string of the original array BEFORE sorting
               String beforeSort = buildArrayString(inputs);

               // perform the radix sort on the inputs array
               radixSort(inputs);

               // build the sorted output string AFTER sorting
               String afterSort = buildArrayString(inputs);

               // display results
               System.out.println("Array values before sorting: " + beforeSort);
               System.out.println("Array values after sorting:  " + afterSort);
               break;

            case 4: // Exit program
               System.out.println("Exiting program.");
               break;

            default: // invalid option
               System.out.println("Invalid option. Please enter a number between 1 and 4.");
               break;

         }

         System.out.println(); // blank line after each option for readability

      } while (option != 4); // loop until user exits

      input.close(); // close scanner before program terminates

   }

   /*
    * Applies the Radix Sort algorithm to the given array.
    * Sorts by processing one digit position at a time, starting at the
    * least significant digit (ones place) and ending at the digit position
    * of the largest value. Uses Q0-Q9 queues for bucketing.
    */
   public static void radixSort(int[] arr) {
      // find the maximum value to determine how many digit passes are needed
      int maxVal = arr[0];
      for (int i = 1; i < arr.length; i++) {
         if (arr[i] > maxVal) {
            maxVal = arr[i];
         }
      }

      int numDigits = CountDigits(maxVal); // number of passes = digits in largest value

      // perform one pass for each digit position (ones, tens, hundreds, ...)
      for (int pass = 1; pass <= numDigits; pass++) {

         // --- Distribution phase: place each element into the correct bucket ---
         for (int i = 0; i < arr.length; i++) {
            int digit = ExtractDigit(arr[i], pass); // get the digit for this pass
            Q[digit].enqueue(arr[i]); // enqueue into corresponding queue
         }

         // --- Collection phase: gather elements back into the array in order ---
         int index = 0;
         for (int d = 0; d <= 9; d++) {
            // dequeue all elements from Q[d] and put them back in the array
            while (!Q[d].isEmpty()) {
               arr[index] = Q[d].dequeue();
               index++;
            }
         }
      }
   }

   // mathematically extracts the digit at the given position from a number.
   // Position 1 = ones place, position 2 = tens place, position 3 = hundreds, etc.
   public static int ExtractDigit(int number, int position) {
      // divide out lower positions, then take modulo 10 to isolate the target digit
      // example: ExtractDigit(3465, 2) => 3465 / 10 = 346, 346 % 10 = 6
      int divisor = 1;
      for (int i = 1; i < position; i++) {
         divisor *= 10; // compute 10^(position-1)
      }
      return (number / divisor) % 10;
   }

   // Counts the number of digits in a non-negative integer.
   // Used to determine how many Radix Sort passes are required.
   public static int CountDigits(int number) {
      if (number == 0) {
         return 1; // zero has exactly one digit
      }

      int count = 0;
      while (number > 0) {
         number /= 10; // strip the least significant digit each iteration
         count++;
      }

      return count;
   }

   // Converts an integer array into a comma-separated string representation for display purposes.
   public static String buildArrayString(int[] arr) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < arr.length; i++) {
         sb.append(arr[i]);
         if (i < arr.length - 1) {
            sb.append(", "); // separate values with a comma and space
         }
      }
      return sb.toString();
   }

   // displays the main menu to the user.
   public static void printMenu() {
      System.out.println();
      System.out.println("--------------MAIN MENU--------------");
      System.out.println("1 - Read array size");
      System.out.println("2 - Read array values");
      System.out.println("3 - Run Radix Sort and print outputs");
      System.out.println("4 - Exit program");
      System.out.println();
   }
}