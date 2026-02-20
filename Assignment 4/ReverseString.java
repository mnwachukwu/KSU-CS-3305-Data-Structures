// Name:        Matt Nwachukwu
// Class        CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell Bradley
// Assignment:  4
// IDE Name:    VS Code

import java.util.Scanner;

public class ReverseString {
    public static void main(String[] args) {
        MyStack<String> stack = new MyStack<>(); // stack to store words
        Scanner scanner = null; // input reader
        int input = 0; // menu input tracker
        String inputString = ""; // user-entered sentence

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
                    inputString = scanner.nextLine();
                    break;

                case 2: // reverse string and display output
                    if (inputString.equals("")) {
                        System.out.println("No input string found. Please enter a string first.");
                        break;
                    }

                    // clear stack before reuse
                    stack.clear();

                    // split sentence into words
                    String[] words = inputString.split("\\s+");

                    // push words onto stack
                    for (String word : words) {
                        stack.push(word);
                    }

                    // display original string
                    System.out.println("Entered string:\t " + inputString);

                    // build reversed string
                    String reversed = "";
                    while (!stack.isEmpty()) {
                        reversed += stack.pop() + " ";
                    }

                    System.out.println("Reversed String:\t " + reversed.trim());
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
        System.out.println("2- Reverse string and display outputs");
        System.out.println("3- Exit program");
        System.out.println();
        System.out.print("Enter option number: ");
        System.out.println();
    }
}
