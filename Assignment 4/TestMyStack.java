// Name:        Matt Nwachukwu
// Class        CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell
// Assignment:  4
// IDE Name:    VS Code

import java.util.Scanner;

public class TestMyStack {
    public static void main(String[] args) {
        MyStack<Integer> myStack = new MyStack<>(); // create stack object
        Scanner scanner = null; // input reader
        int input = 0; // menu input tracker
        int stackInput = 0; // stack element input tracker

        // keep going while input is not 7 (exit condition)
        while (input != 7) {
            // init
            scanner = new Scanner(System.in);
            printMainMenu();
            input = scanner.nextInt();

            // read input, act accordingly with output
            switch (input) {
                case 1: // push element
                    System.out.print("Push Element. Enter a Number: ");

                    stackInput = scanner.nextInt();

                    System.out.print("Stack contents before pushing " + stackInput + " is:\t" + myStack.toString());

                    myStack.push(stackInput);

                    System.out.print("\nStack contents after pushing " + stackInput + " is:\t" + myStack.toString());
                    break;

                case 2: // pop element
                    System.out.println("Pop Element.");

                    System.out.print("Stack contents before popping is:\t" + myStack.toString());

                    try {
                        Integer popped = myStack.pop();
                        System.out.println("\nPopped element: " + popped);
                    } catch (UnsupportedOperationException e) {
                        System.out.println("\nStack is empty.");
                        continue;
                    }

                    System.out.print("Stack contents after popping is:\t" + myStack.toString());
                    break;

                case 3: // get top element
                    System.out.println("Get Top Element.");

                    System.out.print("Stack contents before getting top is:\t" + myStack.toString());

                    try {
                        Integer top = myStack.top();
                        System.out.println("\nTop element is: " + top);
                    } catch (UnsupportedOperationException e) {
                        System.out.println("\nStack is empty.");
                        continue;
                    }

                    System.out.print("Stack contents after getting top is:\t" + myStack.toString());
                    break;

                case 4: // get stack size
                    System.out.print("Stack contents before getting size is:\t" + myStack.toString());

                    System.out.println("\nStack size is: " + myStack.size());
                    break;

                case 5: // is empty stack?
                    System.out.print("Stack contents before checking empty is:\t" + myStack.toString());

                    if (myStack.isEmpty()) {
                        System.out.println("\nStack is empty.");
                    } else {
                        System.out.println("\nStack is not empty.");
                    }
                    break;

                case 6: // print stack
                    System.out.print("The current stack: " + myStack.toString());

                    if (myStack.isEmpty()) {
                        System.out.println("Stack is empty.");
                    }

                    System.out.println();
                    break;

                case 7: // exit
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
        System.out.println("1- Push element");
        System.out.println("2- Pop element");
        System.out.println("3- Get top element");
        System.out.println("4- Get stack size");
        System.out.println("5- Is empty stack?");
        System.out.println("6- Print stack");
        System.out.println("7- Exit program");
        System.out.println();
        System.out.print("Enter option number: ");
    }
}
