// Name:        Matt Nwachukwu
// Class        CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell
// Assignment:  4
// IDE Name:    VS Code

import java.util.Scanner;

public class CheckPalindrome {
    public static void main(String[] args) {
        MyStack<Character> stack = new MyStack<>(); // stack to store characters
        Scanner scanner = null; // input reader
        int input = 0; // menu input tracker
        String stringInput = ""; // user-entered string

        // keep going while input is not 3 (exit condition)
        while (input != 3) {
            // init
            scanner = new Scanner(System.in);
            printMainMenu();
            input = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (input) {
                case 1: // read input string
                    System.out.print("Enter a string of words: ");
                    stringInput = scanner.nextLine();
                    break;

                case 2: // check palindrome
                    if (stringInput.equals("")) {
                        System.out.println("No input string found. Please enter a string first.");
                        break;
                    }

                    // clear stack before reuse
                    stack.clear();

                    // normalize string: remove spaces and convert to lower case
                    String normalized = stringInput.replaceAll("\\s+", "").toLowerCase();

                    // push characters onto stack
                    for (int i = 0; i < normalized.length(); i++) {
                        stack.push(normalized.charAt(i));
                    }

                    // check palindrome by comparing characters
                    boolean isPalindrome = true;
                    for (int i = 0; i < normalized.length(); i++) {
                        if (normalized.charAt(i) != stack.pop()) {
                            isPalindrome = false;
                            break;
                        }
                    }

                    // display results
                    System.out.println("Entered string:\t " + stringInput);

                    if (isPalindrome) {
                        System.out.println("Palindrome");
                    } else {
                        System.out.println("Not Palindrome");
                    }
                    break;

                case 3: // exit program
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
        System.out.println();
        System.out.println("------------- MAIN MENU -------------");
        System.out.println("1- Read input string of words");
        System.out.println("2- Check Palindrome");
        System.out.println("3- Exit program");
        System.out.println();
        System.out.print("Enter option number: ");
        System.out.println();
    }
}
