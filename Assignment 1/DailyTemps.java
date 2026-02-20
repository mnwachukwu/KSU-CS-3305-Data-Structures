// Name:		Matt Nwachukwu
// Class:	    CS 3305/Section04
// Term:		Spring 2026
// Instructor:  Professor Maxwell Bradley
// Assignment:  1
// IDE Name:    VS Code	

public class DailyTemps {

    private int[] temps; // index 0 = Monday, 1 = Tuesday, etc.

    private static final String[] DAYS = {
            "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday"
    };

    // Constructor receives all daily temperatures
    public DailyTemps(int[] temps) {
        if (temps.length == 7) {
            this.temps = temps;
        }
    }

    // Reset temperature for a given day (by index)
    public void setTemp(int dayIndex, int temperature) {
        if (dayIndex >= 0 || dayIndex <= 6) {
            temps[dayIndex] = temperature;
        }
    }

    // Returns number of freezing days (< 32F)
    public int Freezing() {
        int count = 0;
        for (int t : temps) {
            if (t < 32) {
                count++;
            }
        }
        return count;
    }

    // Returns the name of the warmest day (first if tie)
    public String Warmest() {
        int maxIndex = 0;
        for (int i = 1; i < temps.length; i++) {
            if (temps[i] > temps[maxIndex]) {
                maxIndex = i;
            }
        }
        return DAYS[maxIndex];
    }

    // Prints all temperatures in the required format
    public void printTemps() {
        for (int i = 0; i < temps.length; i++) {
            System.out.printf("%-10s %d%n", DAYS[i], temps[i]);
        }
    }
}
