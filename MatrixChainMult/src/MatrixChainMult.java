import java.util.Random;

public class MatrixChainMult {
    //Generate set of n multiplication-compatible matrix sizes
    static int[] generateSizes(int n, int seed) {
        Random random = new Random(seed);
        int[] array = new int[n + 1];

        for (int i = 0; i < array.length; i++) {
            //Values are multiples of 5 whose range is between 20 and 75
            array[i] = (4 + random.nextInt(12)) * 5;
        }
        return array;
    }

    //Compute cost of multiplying a sequence of matrices from left to right order
    static int leftToRight(int[] arr) {
        int cost = 0;
        for (int i = 1; i < arr.length - 1; i++) {
            cost += arr[0] * arr[i] * arr[i + 1];
        }
        return cost;
    }

    //Compute cost of the optimal matrix chain-multiplication using dynamic programming
    static int matrixChain(int[] p) {
        int n = p.length;
        int[][] m = new int[n][n];

        int i, j, k, l, q;

        for (i = 1; i < n; i++) {
            m[i][i] = 0;
        }

        for (l = 2; l < n; l++) {
            for (i = 1; i < n - l + 1; i++) {
                j = i + l - 1;
                if (j == n)
                    continue;
                m[i][j] = Integer.MAX_VALUE;

                for (k = i; k <= j - 1; k++) {
                    q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j])
                        m[i][j] = q;
                }
            }
        }

        return m[1][n-1];
    }

    //Compute expected improvements or savings for each multiplication
    static double computeImprovement(int optimal, int normal) {
        return (1.00 - (float)optimal/(float)normal) * 100;
    }

    public static void main(String[] args){
        int[] n = {25, 30, 40, 50};
        int[] size;

        int optimalCost = 0;
        int normalCost = 0;

        for (int i: n) {
            //Number of operations for 10 sequences of matrices
            for (int j = 0; j < 10; j++) {
                size = generateSizes(i, j);
                //Optimal number of operations using dynamic programming
                optimalCost += matrixChain(size);
                //Number of operations done from left to right
                normalCost += leftToRight(size);
            }

            //Compute average number of operations
            optimalCost /= 10;
            normalCost /= 10;

            //Output
            System.out.println("For n = " + i );
            System.out.println("Average optimal cost is " + optimalCost);
            System.out.println("Average normal cost is " + normalCost);
            System.out.print("Expected improvement is " );
            System.out.printf("%.2f", computeImprovement(optimalCost, normalCost));
            System.out.println("%" + "\n");
        }
    }
}
