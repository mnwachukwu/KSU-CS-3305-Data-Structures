// Name:		Matt Nwachukwu
// Class:	    CS 3305/Section04
// Term:		Spring 2026
// Instructor:  Professor Maxwell Bradley
// Assignment:  1
// IDE Name:    VS Code	

// definition of a rectangle
class Rectangle {
    private double width = 1;
    private double height = 1;

    // default ctor, instantiates with 1 for width and height
    public Rectangle() {
        width = 1;
        height = 1;
    }

    // ctor with given height and width
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    // returns width
    public double GetWidth() {
        return width;
    }

    // returns height
    public double GetHeight() {
        return height;
    }

    // finds an area
    public double GetArea() {
        return width * height;
    }

    // finds a perimeter
    public double GetPerimeter() {
        return width * 2 + height * 2;
    }

    // prints the rectangles stats
    public String PrintRectangle(String name) {
        return "Rectangle " + name + " is " + width + " units wide and " + height + " units high.";
    }
}