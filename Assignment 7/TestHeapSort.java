// Name:        Matt Nwachukwu
// Class:       CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Bradley
// Assignment:  7
// IDE Name:    VS Code

/**
 * Demonstrates and verifies HeapSort across three different element types:
 * Integer, Character, and String. Each list is printed before and after
 * sorting so the results are easy to confirm at a glance.
 */
public class TestHeapSort {

    /** Entry point: sorts three hard-coded lists and prints the results. */
    public static void main(String[] args) {

        // Integer list from the textbook example (includes negatives and duplicates)
        Integer[] list = { -44, -5, -3, 3, 3, 1, -4, 0, 1, 2, 4, 5, 53 };

        System.out.print("Original List:\t");
        for (int i = 0; i < list.length; i++)
            System.out.print(list[i] + " ");

        HeapSort.heapSort(list);

        System.out.print("\n\nSorted List:\t");
        for (int i = 0; i < list.length; i++)
            System.out.print(list[i] + " ");

        System.out.println("\n");

        // Character list: a mix of upper-case, lower-case, and digit characters
        Character[] list2 = {
                'w', 'f', 'A', 'X', 'T', 'Q', 'k', 's', '8', 'L', '3', 'b', 'A', 'w', 's', 'H', 'j', 'K', 'L'
        };

        System.out.print("Original List:\t");
        for (int i = 0; i < list2.length; i++)
            System.out.print(list2[i] + " ");

        HeapSort.heapSort(list2);

        System.out.print("\n\nSorted List:\t");
        for (int i = 0; i < list2.length; i++)
            System.out.print(list2[i] + " ");

        System.out.println("\n");

        // String list: a collection of words that sort lexicographically
        String[] list3 = { "Data", "Structure", "Is", "Hard", "Computing", "Class", "To Pass" };

        System.out.print("Original List:\t");
        for (int i = 0; i < list3.length; i++)
            System.out.print(list3[i] + " ");

        HeapSort.heapSort(list3);

        System.out.print("\n\nSorted List:\t");
        for (int i = 0; i < list3.length; i++)
            System.out.print(list3[i] + " ");

        System.out.println();
    }
}
