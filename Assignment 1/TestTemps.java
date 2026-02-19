// Name:		Matt Nwachukwu
// Class:	    CS 3305/Section04
// Term:		Spring 2026
// Instructor:  Professor Maxwell
// Assignment:  1
// IDE Name:    VS Code	

import java.util.Scanner;

// test for DailyTemps class
public class TestTemps {
    // entry point for test class
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int[] temps = new int[7];

        System.out.println("Enter daily temperatures for the week:");

        String[] days = {
                "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday", "Sunday"
        };

        for (int i = 0; i < 7; i++) {
            System.out.print(days[i] + ": ");
            temps[i] = input.nextInt();
        }

        DailyTemps week = new DailyTemps(temps);

        System.out.println("\nTesting method printTemps:");
        week.printTemps();

        System.out.println("\nTesting method Freezing:");
        System.out.println("Number of freezing days is " +
                week.Freezing() + " days");

        System.out.println("\nTesting method Warmest:");
        System.out.println("The warmest day of the week is " +
                week.Warmest());

        System.out.println("\nTesting method setTemp:");
        System.out.print("Enter day index to change (0=Monday ... 6=Sunday): ");
        int dayIndex = input.nextInt();

        System.out.print("Enter new temperature: ");
        int newTemp = input.nextInt();

        week.setTemp(dayIndex, newTemp);

        System.out.println("\nUpdated temperatures:");
        week.printTemps();

        input.close();
    }
}
