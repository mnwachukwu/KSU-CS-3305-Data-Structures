// Name:		Matt Nwachukwu
// Class:	    CS 3305/Section04
// Term:		Spring 2026
// Instructor:  Professor Maxwell Bradley
// Assignment:  1
// IDE Name:    VS Code	

import java.util.Scanner;

// test class for Rectangle
class TestRectangle {
    // main method to test Rectangle class
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Rectangle myRectangle = new Rectangle();

        System.out.print("Enter a width for your rectangle: ");
        double width = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter a height for your rectangle: ");
        double height = Double.parseDouble(scanner.nextLine());

        Rectangle yourRectangle = new Rectangle(width, height);

        System.out.println("My Rectangle");
        System.out.println("Width: " + myRectangle.GetWidth());
        System.out.println("Height: " + myRectangle.GetHeight());
        System.out.println("Area: " + myRectangle.GetArea());
        System.out.println("Perimeter: " + myRectangle.GetPerimeter());
        System.out.println(myRectangle.PrintRectangle("myRectangle"));

        System.out.println();

        System.out.println("Your Rectangle");
        System.out.println("Width: " + yourRectangle.GetWidth());
        System.out.println("Height: " + yourRectangle.GetHeight());
        System.out.println("Area: " + yourRectangle.GetArea());
        System.out.println("Perimeter: " + yourRectangle.GetPerimeter());
        System.out.println(yourRectangle.PrintRectangle("yourRectangle"));

        scanner.close();
    }
}