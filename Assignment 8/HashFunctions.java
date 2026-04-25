// Name:        Matt Nwachukwu
// Class:       CS 3305 / Section 04
// Term:        Fall 2025
// Instructor:  Professor Bradley
// Assignment:  8
// IDE Name:    VS Code

import java.util.Scanner;

/**
 * HashFunctions
 *
 * Implements four hash functions applied to a fixed set of 50 unique integer
 * keys.
 * 
 * The hash table is a 50-row, 2-column array: column 0 holds the key placed at
 * that
 * index, and column 1 holds the number of collision probes required to place
 * it.
 * 
 * A probe count of 0 means the key landed directly at its home index with no
 * collision.
 * 
 * Each collision encountered (an occupied slot that had to be skipped) adds 1
 * to the
 * probe count.
 */
public class HashFunctions {
    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /**
     * The fixed, hard-coded set of 50 unique keys as specified by the assignment.
     */
    private static final int[] KEYS = { 1234, 8234, 7867, 1009, 5438,
            4312, 3420, 9487, 5418, 5299, 5078, 8239, 1208, 5098, 5195,
            5329, 4543, 3344, 7698, 5412, 5567, 5672, 7934, 1254, 6091,
            8732, 3095, 1975, 3843, 5589, 5439, 8907, 4097, 3096, 4310,
            5298, 9156, 3895, 6673, 7871, 5787, 9289, 4553, 7822, 8755,
            3398, 6774, 8289, 7665, 5523 };

    /**
     * Number of slots in the hash table. Equals the number of keys.
     */
    private static final int TABLE_SIZE = 50;

    /**
     * Maximum number of additional probe attempts (beyond the initial home-slot
     * check)
     * allowed per key before declaring it un-hashable. This cap prevents infinite
     * loops
     * when a probing sequence cycles back through occupied slots without covering
     * the full table. This can happen in HF3 when H2(key) shares a factor with
     * TABLE_SIZE.
     */
    private static final int MAX_PROBES = 50;

    // -------------------------------------------------------------------------
    // HF4-specific constants
    // -------------------------------------------------------------------------

    /**
     * Knuth's multiplicative constant: floor(2^32 / phi), where phi = (1 + sqrt(5))
     * / 2 ~= 1.6180339887.
     * The value is 2,654,435,769. This is the cornerstone of Fibonacci hashing.
     *
     * Why this works: phi^-1 = (sqrt(5) - 1) / 2 ~= 0.6180339887 is the
     * "most irrational" number. The fractional parts of n * phi^-1 for n =
     * 0,1,2,... are distributed across [0, 1) as evenly as any sequence can be
     * (guaranteed by the three-distance theorem). Multiplying a key by this
     * constant and
     * extracting the fractional portion of the product gives an index that reflects
     * the
     * golden-ratio distribution: keys that are close in value hash to indices that
     * are far apart.
     * 
     * Stored as a long to prevent signed overflow during multiplication.
     */
    private static final long KNUTH_CONSTANT = 2654435769L;

    /**
     * A secondary multiplicative constant used exclusively for computing the
     * double-hashing step in HF4. It is a prime number close to KNUTH_CONSTANT but
     * deliberately different.
     * 
     * Using a distinct constant ensures that the step hash produces an output
     * independent of the
     * primary hash. Independence is critical: if two keys collide at the primary
     * hash and both used
     * the same constant for the step, they would follow the exact same probe
     * sequence and keep colliding,
     * hence the use of double hashing.
     */
    private static final long STEP_CONSTANT = 2654435761L;

    /**
     * The 20 integers in [1, 49] that are coprime with TABLE_SIZE (50).
     *
     * For double hashing to guarantee that the probe sequence visits every slot in
     * a table of size N before repeating, gcd(step, N) must equal 1. Because 50 = 2
     * *
     * 5^2, a value is coprime with 50 if and only if it is: odd, AND not divisible
     * by 5.
     * The count of valid steps is Euler's totient phi(50), that is
     * phi(50) = 50 * (1 - 1/2) * (1 - 1/5) = 20 values.
     *
     * The secondary hash selects one of these 20 values, guaranteeing that every
     * run of HF4
     * uses a step that allows full table coverage.
     */
    private static final int[] VALID_STEPS = {
            1, 3, 7, 9, 11, 13, 17, 19, 21, 23,
            27, 29, 31, 33, 37, 39, 41, 43, 47, 49
    };

    // -------------------------------------------------------------------------
    // Main program
    // -------------------------------------------------------------------------

    /**
     * Displays the menu and dispatches to the selected hash
     * function in a loop until the user selects option 5 (Exit).
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        // Loop: display the menu, read the selection, and run the chosen function.
        // A fresh 50x2 table is allocated for each run so that runs are independent.
        do {
            displayMenu();
            choice = scanner.nextInt();

            if (choice == 1) {
                // HF1: Division method with Linear Probing.
                int[][] table = new int[TABLE_SIZE][2];
                HF1(table);
                displayTable(table, "HF1");
            } else if (choice == 2) {
                // HF2: Division method with Quadratic Probing.
                int[][] table = new int[TABLE_SIZE][2];
                HF2(table);
                displayTable(table, "HF2");
            } else if (choice == 3) {
                // HF3: Division method with Double Hashing.
                int[][] table = new int[TABLE_SIZE][2];
                HF3(table);
                displayTable(table, "HF3");
            } else if (choice == 4) {
                // HF4: Fibonacci (Multiplicative) Hashing with Double Hashing.
                int[][] table = new int[TABLE_SIZE][2];
                HF4(table);
                displayTable(table, "HF4");
            } else if (choice == 5) {
                System.out.println("Exiting program.");
            } else {
                System.out.println("Invalid option. Please enter 1 through 5.");
            }
        } while (choice != 5);

        scanner.close();
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Prints the main menu to standard output, surrounded by blank lines for
     * readability as required by the assignment specification.
     */
    private static void displayMenu() {
        System.out.println();
        System.out.println("-----MAIN MENU-------------------------------------");
        System.out.println("1. Run HF1 (Division method with Linear Probing)");
        System.out.println("2. Run HF2 (Division method with Quadratic Probing)");
        System.out.println("3. Run HF3 (Division method with Double Hashing)");
        System.out.println("4. Run HF4 (Student Designed HF)");
        System.out.println("5. Exit program");
        System.out.print("Enter option number: ");
    }

    /**
     * Prints the contents of the hash table followed by the total probe count.
     * Empty slots (key == 0, meaning no key was ever placed there) are shown with
     * dashes. Blank lines are added before and after the table for readability.
     *
     * @param table  the populated 50x2 hash table (column 0: key, column 1: probes)
     * @param hfName the name of the hash function used to build this table
     */
    private static void displayTable(int[][] table, String hfName) {
        System.out.println();
        System.out.println("Hash table resulted from " + hfName + ":");
        System.out.println();
        System.out.printf("%-8s  %-10s  %-8s%n", "Index", "Key", "Probes");
        System.out.println("------------------------");

        for (int i = 0; i < TABLE_SIZE; i++) {
            // A key value of 0 in column 0 signals that this slot was never filled.
            // None of the 50 keys are 0, so 0 is a safe sentinel for "empty."
            if (table[i][0] == 0) {
                System.out.printf("  %-6d  %-10s  %-8s%n", i, "---", "---");
            } else {
                System.out.printf("  %-6d  %-10d  %-8d%n", i, table[i][0], table[i][1]);
            }
        }

        System.out.println("------------------------");
        System.out.println();
        System.out.println("Sum of probe values = " + sumProbes(table) + " probes.");
        System.out.println();
    }

    /**
     * Sums the probe values (column 1) across all occupied entries in the table.
     * Empty slots (key == 0) are excluded, as they represent positions where no
     * key was placed and therefore have no meaningful probe count. The total gives
     * a single-number measure of hash function efficiency: lower is better.
     *
     * @param table the 50x2 hash table to evaluate
     * @return the sum of all probe counts for placed keys
     */
    private static int sumProbes(int[][] table) {
        int sum = 0;

        for (int i = 0; i < TABLE_SIZE; i++) {
            // Only count rows that contain an actual key.
            if (table[i][0] != 0) {
                sum += table[i][1];
            }
        }

        return sum;
    }

    // -------------------------------------------------------------------------
    // Hash functions
    // -------------------------------------------------------------------------

    /**
     * HF1: Division Method with Linear Probing.
     *
     * Primary hash: h(key) = key % TABLE_SIZE.
     *
     * Collision resolution: on each collision, advance by 1 (wrapping via modulo).
     * Because gcd(1, TABLE_SIZE) = 1 for any TABLE_SIZE, linear probing always
     * visits every slot and guarantees placement as long as any empty slot exists.
     *
     * @param table the empty 50x2 table to populate
     */
    private static void HF1(int[][] table) {
        for (int i = 0; i < KEYS.length; i++) {
            int key = KEYS[i];

            // Compute the home index using the division method.
            // The home index is the slot this key ideally belongs in before collisions.
            int index = key % TABLE_SIZE;

            // probes counts the number of occupied slots encountered before landing.
            // 0 means the home slot was empty: no collision resolution needed.
            int probes = 0;

            // Advance linearly until we find an empty slot (sentinel value 0).
            // Each occupied slot we skip costs one probe.
            while (table[index][0] != 0) {
                probes++;

                // Wrap around to the beginning of the table when we reach the end.
                index = (index + 1) % TABLE_SIZE;
            }

            // Place the key at the first empty slot found, along with its probe count.
            table[index][0] = key;
            table[index][1] = probes;
        }
    }

    /**
     * HF2: Division Method with Quadratic Probing.
     *
     * Primary hash: h(key) = key % TABLE_SIZE.
     *
     * Collision resolution: on the j-th attempt, probe index = (home + j^2) %
     * TABLE_SIZE.
     * The offset from the home index grows quadratically (1, 4, 9, 16, ...), which
     * eliminates primary clustering: keys displaced from the same home slot jump to
     * different locations rather than piling up in a single consecutive run.
     *
     * @param table the empty 50x2 table to populate
     */
    private static void HF2(int[][] table) {
        for (int i = 0; i < KEYS.length; i++) {
            int key = KEYS[i];

            // Compute and remember the home index. The quadratic formula always adds
            // j^2 to the HOME index, not to the previously probed position. Forgetting
            // this and applying j^2 incrementally is a common implementation error.
            int homeIndex = key % TABLE_SIZE;
            int index = homeIndex;
            int probes = 0;

            // j tracks the current probe attempt number, starting at 1 for the first
            // collision. The probe position is: (homeIndex + j^2) % TABLE_SIZE.
            int j = 1;

            while (table[index][0] != 0) {
                probes++;

                // Compute the next candidate: home + j^2, wrapped to table bounds.
                // j is then incremented so the next iteration uses (j+1)^2.
                index = (homeIndex + j * j) % TABLE_SIZE;
                j++;
            }

            table[index][0] = key;
            table[index][1] = probes;
        }
    }

    /**
     * HF3: Division Method with Double Hashing.
     *
     * Primary hash: h1(key) = key % TABLE_SIZE.
     * Secondary hash: H2(key) = 30 - (key % 25).
     * Probe formula: index = (h1(key) + j * H2(key)) % TABLE_SIZE, j = 1, 2, 3, ...
     *
     * Double hashing eliminates both primary and secondary clustering.
     *
     * @param table the empty 50x2 table to populate
     */
    private static void HF3(int[][] table) {
        for (int i = 0; i < KEYS.length; i++) {
            int key = KEYS[i];

            // Compute the home index using the division method.
            int homeIndex = key % TABLE_SIZE;
            int index = homeIndex;
            int probes = 0;

            // Only enter collision resolution if the home slot is already occupied.
            if (table[index][0] != 0) {
                // The home slot is occupied: that itself counts as the first collision probe.
                probes = 1;

                // Compute the secondary hash step. H2 produces values in [6, 30]
                // because key % 25 ranges from 0 to 24, so 30 - (key % 25) ranges from 6 to 30.
                int h2 = 30 - (key % 25);

                // Flag to detect successful placement within MAX_PROBES attempts.
                boolean placed = false;

                for (int j = 1; j <= MAX_PROBES; j++) {
                    // Compute the next probe position using the double hashing formula.
                    // Each step of j multiplies H2 by one more, advancing further from home.
                    index = (homeIndex + j * h2) % TABLE_SIZE;

                    if (table[index][0] == 0) {
                        // Found an empty slot: break and place the key here.
                        placed = true;
                        break;
                    }

                    // This slot was also occupied: count another collision probe.
                    probes++;
                }

                if (!placed) {
                    // The probe sequence exhausted MAX_PROBES attempts without finding
                    // a free slot. This happens when gcd(H2, 50) > 1, causing the sequence
                    // to cycle through a subset of slots that are all occupied.
                    // Skip this key and move on without storing it in the table.
                    System.out.println("Unable to hash key " + key + " to the table.");
                    continue;
                }
            }

            table[index][0] = key;
            table[index][1] = probes;
        }
    }

    /*
     * =========================================================================
     * HF4: Fibonacci (Multiplicative) Hashing with Double Hashing
     * =========================================================================
     *
     * OVERVIEW
     * --------
     * I have tried to implement Multiplicative Hasing with Double Hasing.
     * 
     * It uses the multiplicative (Fibonacci) method for the primary hash and double
     * hashing for collision
     * resolution, with the step derived from a second independent multiplicative
     * hash.
     *
     * 
     * PRIMARY HASH: The Multiplicative (Fibonacci) Method
     * ----------------------------------------------------
     * The division method is a weak primary hash for these particular keys because:
     * 
     * 1. It only examines the low-order bits of the key via modulo. Two keys
     * that differ only in their hundreth digit (e.g., 5098 and 7698, both
     * divisible by 98) map to the same remainder, causing guaranteed collisions.
     * 
     * 2. Patterns in the key set (many keys ending in 98, 95, 39, etc.) cause
     * heavy clustering around a few indices, leading to long probe chains.
     *
     * 
     * This multiplicative method uses ALL bits of the key. The algorithm:
     *
     * Step 1: Multiply the key by a constant A in (0, 1).
     * frac(key * A) will be spread uniformly in [0, 1).
     * Step 2: Multiply the fractional part by TABLE_SIZE.
     * Step 3: Take the floor: index = floor(TABLE_SIZE * frac(key * A)).
     *
     * The ideal constant A is phi^-1 = (sqrt(5) - 1) / 2 ~= 0.6180339887.
     * This is the golden ratio conjugate. It is the "most irrational" number in a
     * rigorous mathematical sense: the three-distance theorem proves that
     * consecutive multiples of phi^-1 (mod 1) are spread as uniformly as any
     * sequence can be.
     * This is why the method is called Fibonacci hashing.
     *
     * To implement this in integer arithmetic without floating-point error, I use
     * Knuth's 32-bit approximation of phi^-1 scaled to the full 32-bit range:
     *
     * KNUTH_CONSTANT = floor(2^32 * phi^-1) = 2,654,435,769
     *
     * Computation in code:
     * primaryProduct = key * KNUTH_CONSTANT (64-bit product)
     * fracPart = primaryProduct mod 2^32 (lower 32 bits of product)
     * index = floor(TABLE_SIZE * fracPart / 2^32) = (fracPart * TABLE_SIZE) >> 32
     *
     * The lower 32 bits of (key * KNUTH_CONSTANT) represent frac(key * phi^-1)
     * scaled to [0, 2^32). Multiplying by TABLE_SIZE and right-shifting by 32
     * converts
     * that scaled fraction into an integer index in [0, TABLE_SIZE). This is
     * mathematically identical to the floating-point formula but uses only integer
     * operations.
     *
     * Crucially, for keys that all collide under division (e.g., 5098, 7698, 5298,
     * 3398, and 8289 all map to index 48 via mod 50), the multiplicative method
     * maps them
     * to entirely distinct indices, greatly reducing initial collision frequency.
     *
     *
     * COLLISION RESOLUTION: Double Hashing with a Second Multiplicative Hash
     * ------------------------------------------------------------------------
     * When the home slot is occupied, HF4 uses double hashing. The step size is
     * computed by applying a second multiplicative hash (using STEP_CONSTANT) to
     * the same key, then selecting from the pre-computed VALID_STEPS array.
     *
     * The step MUST be coprime with TABLE_SIZE (50 = 2 * 5^2) so that the probe
     * sequence visits all 50 slots before repeating. VALID_STEPS contains exactly
     * the 20 integers in [1, 49] satisfying gcd(step, 50) = 1: they are the odd
     * numbers
     * in that range that are also not divisible by 5. Selecting a step directly
     * from this array guarantees full table coverage and eliminates the
     * cycle-induced
     * failures seen in HF3. As a result, HF4 should place all 50 keys.
     *
     * The double hashing probe sequence for a key k with home index h and step s:
     * Probe 0 (initial): h
     * Probe 1 (j=1): (h + 1*s) % TABLE_SIZE
     * Probe 2 (j=2): (h + 2*s) % TABLE_SIZE
     * ...
     * Probe 49 (j=49): (h + 49*s) % TABLE_SIZE
     *
     * With gcd(s, 50) = 1, the 49 positions at j=1..49 cover all slots except h.
     * Assuming we always have an empty slot available (we insert 50 keys into 50
     * slots
     * one by one), the loop finds an empty position before j = 49.
     */

    /**
     * HF4: Fibonacci (Multiplicative) Hashing with Double Hashing.
     *
     * Primary hash: Fibonacci/multiplicative using KNUTH_CONSTANT.
     * 
     * Collision resolution: double hashing with a second multiplicative hash that
     * always produces a step coprime with TABLE_SIZE, guaranteeing full table
     * coverage.
     *
     * See the large design block comment immediately above this method for a full
     * explanation of the algorithm, the math behind the constants, and why this
     * approach outperforms division-based methods.
     *
     * @param table the empty 50x2 table to populate
     */
    private static void HF4(int[][] table) {
        for (int i = 0; i < KEYS.length; i++) {
            int key = KEYS[i];

            // ==================================================================
            // PRIMARY HASH: Fibonacci (Multiplicative) Method
            // ==================================================================

            // Treat the key as an unsigned 32-bit value before multiplying.
            // (ints are signed, so masking with 0xFFFFFFFFL promotes to a
            // positive long. For our key range (all positive 4-digit numbers),
            // this mask has no numerical effect, but it is the correct practice
            // for any key that might occupy the full 32-bit signed range.)
            long primaryProduct = (key & 0xFFFFFFFFL) * KNUTH_CONSTANT;

            // The lower 32 bits of the 64-bit product represent frac(key * phi^-1)
            // scaled to the range [0, 2^32). Extracting them with the bitmask isolates
            // exactly this "fractional part" for the next computation.
            long fracPart = primaryProduct & 0xFFFFFFFFL;

            // Scale the fractional part to [0, TABLE_SIZE) using the same multiplication
            // and right-shift. This computes floor(TABLE_SIZE * fracPart / 2^32),
            // which is the integer form of floor(TABLE_SIZE * frac(key * phi^-1)).
            int index = (int) ((fracPart * TABLE_SIZE) >>> 32);

            // Probe count: number of occupied slots encountered before placing the key.
            // Starts at 0 because the home slot may be empty (no collision to resolve).
            int probes = 0;

            // If the home slot is empty, skip collision resolution entirely.
            if (table[index][0] != 0) {
                // ==============================================================
                // The home slot is occupied: begin collision resolution.
                // Count the home collision as the first probe.
                // ==============================================================
                probes = 1;

                // ==============================================================
                // SECONDARY HASH: Step computation for double hashing
                // ==============================================================

                // Apply a second multiplicative hash using STEP_CONSTANT, a prime
                // distinct from KNUTH_CONSTANT. The structure is identical to the
                // primary hash: multiply, extract lower 32 bits, scale to range.
                long stepProduct = (key & 0xFFFFFFFFL) * STEP_CONSTANT;
                long stepFrac = stepProduct & 0xFFFFFFFFL;

                // Scale to [0, VALID_STEPS.length) to pick an index into the valid
                // step array. Because VALID_STEPS has 20 entries, this selects one
                // of the 20 values in [1, 49] coprime with 50.
                int stepIndex = (int) ((stepFrac * VALID_STEPS.length) >>> 32);

                // Look up the actual step value from the pre-computed array.
                // This guarantees gcd(step, TABLE_SIZE) = 1, so the probe sequence
                // visits all 50 slots before repeating.
                int step = VALID_STEPS[stepIndex];

                // ==============================================================
                // DOUBLE HASHING PROBE LOOP
                // ==============================================================

                // Advance by STEP on each attempt. The probe position after j steps is:
                // (homeIndex + j * step) % TABLE_SIZE
                // computed iteratively as: index = (index + step) % TABLE_SIZE.
                // Both are mathematically equivalent; the iterative form is simpler.
                boolean placed = false;

                for (int j = 1; j <= MAX_PROBES; j++) {
                    // Move to the next candidate slot.
                    index = (index + step) % TABLE_SIZE;

                    if (table[index][0] == 0) {
                        // Found an empty slot. Break and place the key here.
                        placed = true;
                        break;
                    }

                    // This slot was occupied: count another collision probe.
                    probes++;
                }
            }

            // Store the key at the resolved index with its collision probe count.
            table[index][0] = key;
            table[index][1] = probes;
        }
    }
}
