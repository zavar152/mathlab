package itmo.zavar.mathlab.lab.lab4;

import java.util.Arrays;
import java.util.List;

// Java Program to decompose a matrix into
// lower and upper triangular matrix
public class LUDecomposition {

    public static List<double[][]> luDecomposition(double[][] mat, int n) {
        double[][] lower = new double[n][n];
        double[][] upper = new double[n][n];

        // Decomposing matrix into Upper and Lower
        // triangular matrix
        for (int i = 0; i < n; i++) {
            // Upper Triangular
            for (int k = i; k < n; k++) {
                // Summation of L(i, j) * U(j, k)
                int sum = 0;
                for (int j = 0; j < i; j++)
                    sum += (lower[i][j] * upper[j][k]);

                // Evaluating U(i, k)
                upper[i][k] = mat[i][k] - sum;
            }

            // Lower Triangular
            for (int k = i; k < n; k++) {
                if (i == k)
                    lower[i][i] = 1; // Diagonal as 1
                else {
                    // Summation of L(k, j) * U(j, i)
                    int sum = 0;
                    for (int j = 0; j < i; j++)
                        sum += (lower[k][j] * upper[j][i]);

                    // Evaluating L(k, i)
                    lower[k][i]
                            = (mat[k][i] - sum) / upper[i][i];
                }
            }
        }

        return Arrays.asList(lower, upper);
    }
}
