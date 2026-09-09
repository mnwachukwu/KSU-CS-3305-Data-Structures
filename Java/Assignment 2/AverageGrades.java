// Name:        Matt Nwachukwu
// Class        CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Maxwell Bradley
// Assignment:  2
// IDE Name:    VS Code

import java.util.Scanner; // import statement

// average grades program
public class AverageGrades {
    // main method
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in); // input reader
        int input = 0; // menu input tracker
        int classSize = 0; // class size tracker
        int[] classGrades = null; // class grades tracker

        // while the input is not read as 4 (the exit condition)
        while (input != 4) {
            // init
            printMenu();
            input = s.nextInt();

            switch (input) {
                case 1: // class size option
                    System.out.print("Enter the class size: ");
                    s = new Scanner(System.in);
                    classSize = s.nextInt();
                    classGrades = new int[classSize];
                    break;

                case 2: // class grades option
                    if (classGrades != null) {
                        for (int i = 0; i < classSize; i++) {
                            System.out.print("Enter the grade for student " + (i + 1) + " of " + classSize + ": ");
                            s = new Scanner(System.in);
                            classGrades[i] = s.nextInt();
                        }
                    } else {
                        System.out.println("Enter a class size, first.");
                    }
                    break;

                case 3: // class average
                    System.out.println("You entered class size:\t\t" + classSize);
                    System.out.println("You entered grades:\t\t" + classGradesString(classGrades));
                    System.out.println("Class average:\t\t\t" + String.format("%.2f", findAverage(classGrades, 0, 0)));
                    break;

                default: // invalid, or exit condition (the case figures out which one)
                    if (input != 4) {
                        System.out.println("Invalid option.");
                    }
                    break;
            }
        }

        // clean up
        System.out.println("Program execution stopped.");
        s.close();
    }

    // handles printing a menu
    public static void printMenu() {
        System.out.println("\n---------MAIN MENU---------");
        System.out.println("1. Read class size");
        System.out.println("2. Read class grades");
        System.out.println("3. Compute class average");
        System.out.println("4. Exit program");
        System.out.print("\nEnter option number: ");
    }

    // handles finding averages
    public static double findAverage(int[] nums, int index, int sum) {
        // unwind the call stack
        if (index >= nums.length) {
            return (double)sum / nums.length;
        }

        return findAverage(nums, index + 1, sum + nums[index]);
    }

    // prints the class grades as a string
    public static String classGradesString(int[] classGrades){
        String classGradesString = "";

        for (int i = 0; i < classGrades.length; i++){
            classGradesString += classGrades[i] + " ";    
        }

        return classGradesString;
    }
}
