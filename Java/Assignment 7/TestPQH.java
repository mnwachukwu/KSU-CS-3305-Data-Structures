// Name:        Matt Nwachukwu
// Class:       CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Bradley
// Assignment:  7
// IDE Name:    VS Code

import java.util.Scanner;

/**
 * An interactive, menu-driven test program for the PQ_heap class.
 * The user must first select a queue type (Option 0) before any other
 * operation is permitted. The menu is displayed at startup and re-displayed
 * after every completed option until the user chooses to exit.
 */
public class TestPQH {

    // Both queues are allocated up front; only the selected one is used.
    private static PQ_heap<Integer> intQueue = new PQ_heap<Integer>();
    private static PQ_heap<String> strQueue = new PQ_heap<String>();

    // Tracks which queue type the user has selected; null means not yet chosen.
    private static String queueType = null;

    /** Entry point: display the menu and enter the main interaction loop. */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option = -1;

        displayMenu();

        do {
            System.out.print("\nEnter option number: ");

            // Read the full line and parse it; this avoids leftover-newline issues
            String raw = input.nextLine().trim();
            try {
                option = Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                option = -1; // Will fall through to the default case
            }

            // Guard: every option except 0 and 8 requires a queue type to be selected first
            if (option != 0 && option != 8 && queueType == null) {
                System.out.println("\nPlease select a queue type first (Option 0).");
                System.out.println();
                displayMenu();
                continue;
            }

            switch (option) {

                case 0:
                    // Let the user declare whether they want an integer or string queue
                    handleSelectType(input);
                    break;

                case 1:
                    // Enqueue a new element entered by the user
                    handleEnqueue(input);
                    break;

                case 2:
                    // Remove and display the maximum (front) element
                    handleDequeue();
                    break;

                case 3:
                    // Report whether the queue has hit its capacity ceiling
                    handleIsFull();
                    break;

                case 4:
                    // Report whether the queue contains any elements at all
                    handleIsEmpty();
                    break;

                case 5:
                    // Display the current number of elements
                    handleSize();
                    break;

                case 6:
                    // Peek at the maximum without removing it
                    handleFront();
                    break;

                case 7:
                    // Print every element alongside its children in the backing array
                    handlePrint();
                    break;

                case 8:
                    // Graceful exit
                    System.out.println("\nExiting program.");
                    break;

                default:
                    System.out.println("\nInvalid option. Please enter a number between 0 and 8.");
            }

            // Re-display the menu after every option except exit
            if (option != 8) {
                System.out.println();
                displayMenu();
            }

        } while (option != 8);

        input.close();
    }

    // -------------------------------------------------------------------------
    // Menu display
    // -------------------------------------------------------------------------

    /**
     * Print the main menu to standard output.
     * Called at startup and after every completed option.
     */
    private static void displayMenu() {
        System.out.println("----------MAIN MENU-----------");
        System.out.println("0. Enter Queue Type (integer or string)");
        System.out.println("1. Enqueue Element");
        System.out.println("2. Dequeue Element");
        System.out.println("3. Check is_Full");
        System.out.println("4. Check is_Empty");
        System.out.println("5. Print PQueue Size");
        System.out.println("6. Display Front Element");
        System.out.println("7. Print PQueue Elements");
        System.out.println("8. Exit program");
    }

    // -------------------------------------------------------------------------
    // Option handlers
    // -------------------------------------------------------------------------

    /**
     * Option 0: prompt the user to choose between "integer" and "string",
     * then reset both queues so the new session starts fresh.
     */
    private static void handleSelectType(Scanner input) {
        System.out.println("\nTesting method Enter Queue Type (Option 0)");
        System.out.print("Enter queue type (integer or string): ");
        String typeInput = input.nextLine().trim().toLowerCase();

        if (typeInput.equals("integer") || typeInput.equals("string")) {
            queueType = typeInput;
            intQueue = new PQ_heap<Integer>(); // Reset to clear any prior session data
            strQueue = new PQ_heap<String>();
            System.out.println("Queue type set to: " + queueType);
        } else {
            System.out.println("Invalid type. Please enter 'integer' or 'string'.");
        }
    }

    /**
     * Option 1: read a value from the user and insert it into the active queue.
     * Rejects the operation and prints a message if the queue is already full.
     */
    private static void handleEnqueue(Scanner input) {
        System.out.println("\nTesting method Enqueue Element (Option 1)");

        if (queueType.equals("integer")) {
            if (intQueue.is_full()) {
                System.out.println("Queue is full. Cannot enqueue.");
                return;
            }
            System.out.print("Enter integer value to enqueue: ");
            try {
                int val = Integer.parseInt(input.nextLine().trim());
                intQueue.enqueue(val);
                System.out.println("Enqueued: " + val);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        } else {
            if (strQueue.is_full()) {
                System.out.println("Queue is full. Cannot enqueue.");
                return;
            }
            System.out.print("Enter string value to enqueue: ");
            String val = input.nextLine().trim();
            strQueue.enqueue(val);
            System.out.println("Enqueued: " + val);
        }
    }

    /**
     * Option 2: remove and display the maximum element.
     * Prints a warning if the queue is empty rather than crashing.
     */
    private static void handleDequeue() {
        System.out.println("\nTesting method Dequeue Element (Option 2)");

        if (queueType.equals("integer")) {
            if (intQueue.is_empty()) {
                System.out.println("Queue is empty. Nothing to dequeue.");
            } else {
                System.out.println("Dequeued: " + intQueue.dequeue());
            }
        } else {
            if (strQueue.is_empty()) {
                System.out.println("Queue is empty. Nothing to dequeue.");
            } else {
                System.out.println("Dequeued: " + strQueue.dequeue());
            }
        }
    }

    /**
     * Option 3: report whether the active queue has reached its capacity.
     */
    private static void handleIsFull() {
        System.out.println("\nTesting method Check is_Full (Option 3)");
        boolean full = queueType.equals("integer") ? intQueue.is_full() : strQueue.is_full();
        System.out.println("Queue is full: " + full);
    }

    /**
     * Option 4: report whether the active queue is empty.
     */
    private static void handleIsEmpty() {
        System.out.println("\nTesting method Check is_Empty (Option 4)");
        boolean empty = queueType.equals("integer") ? intQueue.is_empty() : strQueue.is_empty();
        System.out.println("Queue is empty: " + empty);
    }

    /**
     * Option 5: display the number of elements currently in the active queue.
     */
    private static void handleSize() {
        System.out.println("\nTesting method Print PQueue Size (Option 5)");
        int sz = queueType.equals("integer") ? intQueue.size() : strQueue.size();
        System.out.println("PQueue size: " + sz);
    }

    /**
     * Option 6: peek at the maximum element without removing it.
     * Prints a warning if the queue is empty.
     */
    private static void handleFront() {
        System.out.println("\nTesting method Display Front Element (Option 6)");

        if (queueType.equals("integer")) {
            if (intQueue.is_empty()) {
                System.out.println("Queue is empty. No front element.");
            } else {
                System.out.println("Front element: " + intQueue.front());
            }
        } else {
            if (strQueue.is_empty()) {
                System.out.println("Queue is empty. No front element.");
            } else {
                System.out.println("Front element: " + strQueue.front());
            }
        }
    }

    /**
     * Option 7: print the heap contents with each node followed by its children,
     * mirroring the parent-child relationships in the backing array.
     * Delegates to the type-appropriate queue.
     */
    private static void handlePrint() {
        System.out.println("\nTesting method Print PQueue Elements (Option 7)");

        if (queueType.equals("integer")) {
            printHeapStructure(intQueue.getHeap());
        } else {
            printHeapStructure(strQueue.getHeap());
        }
    }

    // -------------------------------------------------------------------------
    // Display helper
    // -------------------------------------------------------------------------

    /**
     * Print each element in the heap together with its left and right children.
     * Format for each line: "Index i: parent [leftChild] [rightChild]"
     * Children are omitted when they do not exist (leaf nodes).
     * Prints an empty-queue message when the heap has no elements.
     */
    private static <E extends Comparable<E>> void printHeapStructure(Heap<E> heap) {
        int size = heap.getSize();

        if (size == 0) {
            System.out.println("Queue is empty.");
            return;
        }

        for (int i = 0; i < size; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;

            System.out.print("Index " + i + ": " + heap.get(i));

            if (leftChild < size)
                System.out.print(" " + heap.get(leftChild));
            if (rightChild < size)
                System.out.print(" " + heap.get(rightChild));

            System.out.println();
        }
    }
}
