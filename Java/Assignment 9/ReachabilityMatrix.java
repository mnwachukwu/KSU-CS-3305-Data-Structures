// Name:        Matt Nwachukwu
// Class:       CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Bradley
// Assignment:  9
// IDE Name:    VS Code

import java.util.Scanner;

/*
   This program implements the concept of graph reachability matrices.
   The user provides a directed graph as an adjacency matrix A1, and the
   program computes the integer reachability matrix R = A1 + A2 + ... + AN,
   where Ak denotes the k-th matrix power of A1. From A1 and R the program
   derives ten separate graph metrics and prints them on demand via a menu.
*/
public class ReachabilityMatrix {

    private static int n;               // number of nodes (1 to 5)
    private static int[][] A1;          // adjacency matrix entered by the user
    private static boolean dataEntered; // guards option 2 against premature use

    // -------------------------------------------------------------------------
    // Entry point
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option = 0;

        do {
            System.out.println();
            displayMenu();
            option = input.nextInt();
            System.out.println();

            switch (option) {
                case 1:
                    enterGraphData(input);
                    break;
                case 2:
                    if (!dataEntered) {
                        System.out.println("Error: Please enter graph data first (Option 1).");
                    } else {
                        printOutputs();
                    }
                    break;
                case 3:
                    System.out.println("Exiting program. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        } while (option != 3);

        input.close();
    }

    // -------------------------------------------------------------------------
    // Menu and input
    // -------------------------------------------------------------------------

    /*
       Displays the main menu to the console.
    */
    private static void displayMenu() {
        System.out.println("------MAIN MENU------");
        System.out.println("1. Enter graph data");
        System.out.println("2. Print outputs");
        System.out.println("3. Exit program");
        System.out.print("Enter option number: ");
    }

    /*
       Reads the number of nodes and the adjacency matrix A1 from the user.
       The matrix is read row-by-row, prompting for each individual cell value.
       Sets the dataEntered flag to true when finished so option 2 becomes available.
    */
    private static void enterGraphData(Scanner input) {
        System.out.print("Enter number of nodes (1 to 5): ");
        n = input.nextInt();

        A1 = new int[n][n];

        // read the adjacency matrix one cell at a time, row by row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("Enter A1[" + i + "," + j + "]: ");
                A1[i][j] = input.nextInt();
            }
        }

        dataEntered = true;
        System.out.println("\nGraph data entered successfully.");
    }

    // -------------------------------------------------------------------------
    // Output orchestrator (Option 2)
    // -------------------------------------------------------------------------

    /*
       Computes all intermediate results and calls each of the ten required
       output methods in the order specified by the assignment.
    */
    private static void printOutputs() {
        // build A^1 through A^N; powers[k] holds A^(k+1)
        int[][][] powers = computePowers();

        // R = A^1 + A^2 + ... + A^N
        int[][] R = computeReachabilityMatrix(powers);

        // A^N is the last entry in the powers array
        int[][] AN = powers[n - 1];

        printMatrix(A1);
        System.out.println();
        printReachabilityMatrix(R);
        System.out.println();
        printDegrees(A1);
        System.out.println();
        printOutDegrees(A1);
        System.out.println();
        printSelfLoops(A1);
        System.out.println();
        printCycles(AN);
        System.out.println();
        printPathsOfLength1(A1);
        System.out.println();
        printPathsOfLengthN(AN);
        System.out.println();
        printPathsOfLength1ToN(R);
        System.out.println();
        printCyclesOfLength1ToN(R);
        System.out.println();
        System.out.println("--------------------------------------------------");
    }

    // -------------------------------------------------------------------------
    // Matrix computation helpers
    // -------------------------------------------------------------------------

    /*
       Multiplies two n-by-n integer matrices M1 and M2 using the standard
       triple-loop algorithm and returns the resulting n-by-n matrix.
    */
    private static int[][] multiplyMatrices(int[][] M1, int[][] M2) {
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // dot product of row i of M1 with column j of M2
                for (int k = 0; k < n; k++) {
                    result[i][j] += M1[i][k] * M2[k][j];
                }
            }
        }

        return result;
    }

    /*
       Computes and returns the array of power matrices A^1, A^2, ..., A^N.
       powers[0] = A^1 (the original adjacency matrix).
       powers[k] = A^(k+1) = powers[k-1] multiplied by A1.
    */
    private static int[][][] computePowers() {
        int[][][] powers = new int[n][][];
        powers[0] = A1; // A^1 is the adjacency matrix itself

        for (int k = 1; k < n; k++) {
            // each successive power is the previous one multiplied by A1
            powers[k] = multiplyMatrices(powers[k - 1], A1);
        }

        return powers;
    }

    /*
       Computes the reachability matrix R = A^1 + A^2 + ... + A^N by summing
       corresponding elements across all power matrices and returns R.
    */
    private static int[][] computeReachabilityMatrix(int[][][] powers) {
        int[][] R = new int[n][n];

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    R[i][j] += powers[k][i][j];
                }
            }
        }

        return R;
    }

    // -------------------------------------------------------------------------
    // The ten required output methods
    // -------------------------------------------------------------------------

    /*
       Method 1: Prints the input adjacency matrix A1.
       Each value is followed by two spaces for alignment.
    */
    private static void printMatrix(int[][] matrix) {
        System.out.println("Input Matrix:");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    /*
       Method 2: Prints the reachability matrix R = A^1 + A^2 + ... + A^N.
       Each value is followed by two spaces for alignment.
    */
    private static void printReachabilityMatrix(int[][] R) {
        System.out.println("Reachability Matrix:");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(R[i][j] + "  ");
            }
            System.out.println();
        }
    }

    /*
       Method 3: Prints the in-degree of each node.
       The in-degree of node j is the sum of column j in A1; it counts how many
       edges point INTO that node.
    */
    private static void printDegrees(int[][] matrix) {
        System.out.println("In-degrees:");

        for (int j = 0; j < n; j++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += matrix[i][j]; // accumulate column j
            }
            System.out.println("Node " + (j + 1) + " in-degree is " + sum);
        }
    }

    /*
       Method 4: Prints the out-degree of each node.
       The out-degree of node i is the sum of row i in A1; it counts how many
       edges leave that node.
    */
    private static void printOutDegrees(int[][] matrix) {
        System.out.println("Out-degrees:");

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += matrix[i][j]; // accumulate row i
            }
            System.out.println("Node " + (i + 1) + " out-degree is " + sum);
        }
    }

    /*
       Method 5: Prints the total number of self-loops in the graph.
       A self-loop at node i is represented by a non-zero diagonal element A1[i][i].
       Since the values are 0 or 1, summing the diagonal gives the count directly.
    */
    private static void printSelfLoops(int[][] matrix) {
        int loops = 0;
        for (int i = 0; i < n; i++) {
            loops += matrix[i][i]; // diagonal element: edge from node i to itself
        }
        System.out.println("Total number of self-loops: " + loops);
    }

    /*
       Method 6: Prints the total number of cycles of length exactly N edges.
       This equals the trace (sum of diagonal elements) of A^N, because A^N[i][i]
       counts the number of closed walks of length N starting and ending at node i.
    */
    private static void printCycles(int[][] AN) {
        int cycles = 0;
        for (int i = 0; i < n; i++) {
            cycles += AN[i][i]; // diagonal of A^N counts closed walks of length N
        }
        System.out.println("Total number of cycles of length " + n + " edges: " + cycles);
    }

    /*
       Method 7: Prints the total number of paths of exactly 1 edge.
       This is simply the sum of all elements in A1, since each non-zero entry
       represents one direct edge (path of length 1) between two nodes.
    */
    private static void printPathsOfLength1(int[][] matrix) {
        int total = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                total += matrix[i][j];
            }
        }
        System.out.println("Total number of paths of length 1 edge: " + total);
    }

    /*
       Method 8: Prints the total number of paths of exactly N edges.
       This is the sum of all elements in A^N, since A^N[i][j] counts the number
       of walks of length N from node i to node j.
    */
    private static void printPathsOfLengthN(int[][] AN) {
        int total = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                total += AN[i][j];
            }
        }
        System.out.println("Total number of paths of length " + n + " edges: " + total);
    }

    /*
       Method 9: Prints the total number of paths of length 1 through N edges.
       This is the sum of all elements in the reachability matrix R, because
       R[i][j] = A1[i][j] + A2[i][j] + ... + AN[i][j] counts all walks of any
       length from 1 to N between every pair of nodes.
    */
    private static void printPathsOfLength1ToN(int[][] R) {
        int total = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                total += R[i][j];
            }
        }
        System.out.println("Total number of paths of length 1 to " + n + " edges: " + total);
    }

    /*
       Method 10: Prints the total number of cycles of length 1 through N edges.
       This is the trace (sum of diagonal) of the reachability matrix R, because
       R[i][i] aggregates all closed walks of any length from 1 to N at node i.
    */
    private static void printCyclesOfLength1ToN(int[][] R) {
        int total = 0;
        for (int i = 0; i < n; i++) {
            total += R[i][i]; // diagonal of R: closed walks of length 1 to N at node i
        }
        System.out.println("Total number of cycles of length 1 to " + n + " edges: " + total);
    }
}
