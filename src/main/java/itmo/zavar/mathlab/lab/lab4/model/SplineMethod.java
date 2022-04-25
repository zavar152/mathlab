package itmo.zavar.mathlab.lab.lab4.model;

import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import org.apache.commons.math3.linear.*;
import org.mariuszgromada.math.mxparser.Function;

public final class SplineMethod {

    public static SplineResult calculate(double[] x, double[] y) {
        int n = x.length;
        int equations = 4 * (n - 1);

        double[][] matrix = new double[equations][equations + 1];
        double[][] firstCoefficients = new double[n][4];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4; j++) {
                firstCoefficients[i][j] = Math.pow(x[i], j);
            }
        }
        for (int i = 0; i < n - 1; i++) {
            int k = 0;
            for (int j = 0; j < equations; j += n - 1) {
                matrix[i][j + i] = firstCoefficients[i][k];
                k += 1;
            }
        }
        int f = 0;
        for (int j = n - 1; j <= equations; j += n - 1) {
            matrix[n - 1][j - 1] = firstCoefficients[n - 1][f];
            f++;
        }
        for (int i = 0; i < n; i++) {
            matrix[i][equations] = y[i];
        }

        double[][] secondCoefficients = new double[n - 2][8];

        for (int j = 0; j < n - 2; j++) {
            int k = 0;
            for (int i = 0; i < 8; i += 2) {
                double pow = Math.pow(x[j + 1], k);
                secondCoefficients[j][i] = pow;
                secondCoefficients[j][i + 1] = -pow;
                k++;
            }
        }

        for (int i = 0; i < n - 2; i++) {
            int k = 0;
            for (int j = 0; j < equations; j += n - 1) {
                matrix[n + i][j + i] = secondCoefficients[i][k];
                matrix[n + i][j + 1 + i] = secondCoefficients[i][k + 1];
                k += 2;
            }
        }

        double[][] thirdCoefficients = new double[n - 2][6];
        for (int j = 0; j < n - 2; j++) {
            int k = 0;
            for (int i = 0; i < 6; i += 2) {
                double pow = (k + 1) * Math.pow(x[j + 1], k);
                thirdCoefficients[j][i] = pow;
                thirdCoefficients[j][i + 1] = -pow;
                k++;
            }
        }

        for (int i = 0; i < n - 2; i++) {
            int k = 0;
            for (int j = n - 1; j < equations; j += n - 1) {
                matrix[n - 2 + n + i][j + i] = thirdCoefficients[i][k];
                matrix[n - 2 + n + i][j + 1 + i] = thirdCoefficients[i][k + 1];
                k += 2;
            }
        }

        double[][] forthCoefficients = new double[n - 2][4];
        for (int j = 0; j < n - 2; j++) {
            int k = 0;
            for (int i = 0; i < 4; i += 2) {
                double pow = 0;
                if(i == 0)
                    pow = 2;
                else
                    pow = 6 * x[j + 1];
                forthCoefficients[j][i] = pow;
                forthCoefficients[j][i + 1] = -pow;
                k++;
            }
        }

        for (int i = 0; i < n - 2; i++) {
            int k = 0;
            for (int j = 2 * (n - 1); j < equations; j += n - 1) {
                matrix[2 * (n - 2) + n + i][j + i] = forthCoefficients[i][k];
                matrix[2 * (n - 2) + n + i][j + 1 + i] = forthCoefficients[i][k + 1];
                k += 2;
            }
        }

        matrix[equations - 2][equations-2*(n-1)] = 1;
        matrix[equations - 1][equations-2*(n-1)+(n-2)] = 1;
        matrix[equations - 1][equations-(n-1)+(n-2)] = 3*x[n-1];

        Matrix m = new Matrix("m", matrix);

        RealVector b = new ArrayRealVector(m.getColumn(equations),false);
        RealMatrix a = new Array2DRowRealMatrix(m.getWithoutColumn(equations).getElements(),false);
        DecompositionSolver solver = new LUDecomposition(a).getSolver();
        RealVector solution = solver.solve(b);
        double[] coefs = solution.toArray();
        Function[] splines = new Function[n - 1];

        for (int i = 0; i < n - 1; i++) {
            int k = 0;
            StringBuilder formula = new StringBuilder("f(x)=");
            for (int j = i; j < equations; j+=n-1) {
                formula.append(coefs[j] >= 0 ? "+" + coefs[j] : coefs[j]).append("*x^").append(k);
                k++;
            }
            splines[i] = new Function(formula.toString());
        }

        return new SplineResult(x, y, splines);
    }
}
