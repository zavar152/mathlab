package itmo.zavar.mathlab.lab.lab1.model.gauss;

import com.sun.istack.internal.NotNull;
import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.lab.lab1.model.matrix.MatrixCreator;

import java.util.concurrent.TimeUnit;

public final class GaussAlgorithm {

    public static GaussResult calculate(@NotNull Matrix equationsSystem) {
        Matrix copy = equationsSystem.copy();
        double det0 = equationsSystem.determinant();
        long start = System.nanoTime();
        for(int i = 0; i < equationsSystem.getRowsCount() - 1; i++) {
            pivoting(equationsSystem, i);
            normalizationAndSubtract(equationsSystem, i);
        }
        Matrix x = getAnswer(equationsSystem);
        long time = System.nanoTime() - start;
        Matrix discrepancy = getDiscrepancy(copy, x.getColumn(0));
        double det1 = equationsSystem.determinant();
        return new GaussResult(equationsSystem, x, discrepancy, det0, det1, time);
    }

    private static void pivoting(@NotNull Matrix equationsSystem, int col) {
        double[] column = equationsSystem.getColumn(col);
        int maxAt = 0;
        for (int i = col; i < column.length; i++) {
            maxAt = Math.abs(column[i]) > column[maxAt] ? i : maxAt;
        }
        equationsSystem.exchangeRows(maxAt, col);
    }

    private static void normalizationAndSubtract(@NotNull Matrix equationsSystem, int r) {
        double[][] elements = equationsSystem.getElements();
        for(int i = r + 1; i < equationsSystem.getRowsCount(); i++) {
            double f = elements[i][r] / elements[r][r];
            for(int j = r + 1; j <= equationsSystem.getRowsCount(); j++) {
                elements[i][j] -= elements[r][j] * f;
            }
            elements[i][r] = 0;
        }
    }

    private static Matrix getAnswer(@NotNull Matrix equationsSystem) {
        double[][] elements = equationsSystem.getElements();
        double[] x = new double[equationsSystem.getRowsCount()];
        for(int i = x.length - 1; i >= 0; i--) {
            x[i] = elements[i][x.length];
            for(int j = i + 1; j < x.length; j++) {
                x[i] -= elements[i][j] * x[j];
            }
            x[i] = x[i]/elements[i][i];
        }

        return MatrixCreator.fromColumn("X", x);
    }

    private static Matrix getDiscrepancy(@NotNull Matrix initialSystem, double[] x) {
        double[][] elements = initialSystem.getElements();
        double[] discrepancy = new double[initialSystem.getRowsCount()];
        for(int k = 0; k < x.length; k++) {
            double temp = 0;
            for(int j = 0; j < x.length; j++) {
                temp = temp + elements[k][j] * x[j];
            }
            discrepancy[k] = Math.abs(temp - elements[k][x.length]);
        }
        return MatrixCreator.fromColumn("Discrepancy", discrepancy);
    }

}
