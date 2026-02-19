// Name:        Matt Nwachukwu
// Class        CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell
// Assignment:  2
// IDE Name:    VS Code

import java.util.Scanner; // import statement

// vowels class definition
public class Vowels {
    // entry point of the program
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in); // input reader
        int input = 0; // input tracker
        String inputString = ""; // input string tracker

        // while the input is not 3 (exit condition)
        while (input != 3) {
            // init
            printMenu();
            input = s.nextInt();

            switch (input) {
                case 1: // string input option
                    System.out.print("Enter a string: ");
                    s = new Scanner(System.in);
                    inputString = s.nextLine();
                    
                    break;
                case 2: // string count option
                    if (!inputString.isEmpty()) {
                        System.out.println("You entered string:\t" + inputString);
                        System.out.println("Number of vowels:\t" + countVowels(inputString));
                    } else {
                        System.out.println("Input a string, first.");
                    }
                    break;
                default: // invalid entry, or exit condition (case figures out which one)
                    if (input != 3) {
                        System.out.println("Invalid option.");
                    }
                    break;
            }
        }

        // clean up
        System.out.println("Program execution stopped.");
        s.close();
    }

    // handles printing the main menu
    public static void printMenu() {
        System.out.println("\n---------MAIN MENU---------");
        System.out.println("1. Read input string");
        System.out.println("2. Compute numer of vowels");
        System.out.println("3. Exit program");
        System.out.print("\nEnter option number: ");
    }

    // counts vowels
    public static int countVowels(String str) {
        char firstLetter = str.charAt(0);
        String subString = str.substring(1);

        // unwind the call stack
        if (subString.isEmpty()) {
            return 0;
        }

        if (isVowel(firstLetter)) {
            return 1 + countVowels(subString);
        } else {
            return 0 + countVowels(subString);
        }
    }

    // a method to determine if a character is a lower or upper case vowel
    public static boolean isVowel(char c) {
        return (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' ||
                c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
    }
}
