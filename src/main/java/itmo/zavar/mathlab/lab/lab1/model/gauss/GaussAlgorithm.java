package itmo.zavar.mathlab.lab.lab1.model.gauss;

import itmo.zavar.mathlab.lab.lab1.exception.GaussException;
import itmo.zavar.mathlab.lab.lab1.exception.ZeroColumnException;
import itmo.zavar.mathlab.lab.lab1.exception.ZeroDeterminantException;
import itmo.zavar.mathlab.lab.lab1.exception.ZeroDiagonalException;
import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.lab.lab1.model.matrix.MatrixCreator;

public final class GaussAlgorithm {

    public static GaussResult calculate(Matrix equationsSystem, boolean enablePivoting) throws GaussException {
        if(equationsSystem.isZeroDiagonal() && !enablePivoting)
            throw new ZeroDiagonalException("There are zeros on the diagonal");
        Matrix copy = equationsSystem.copy();
        long start = System.nanoTime();
        for(int i = 0; i < equationsSystem.getRowsCount(); i++) {
            if(enablePivoting)
                pivoting(equationsSystem, i);
            normalizationAndSubtract(equationsSystem, i);
        }
        double newDeterminant = equationsSystem.getWithoutColumn(equationsSystem.getRowsCount()).determinant();
        if(newDeterminant == 0)
            throw new ZeroDeterminantException("Determinant is 0, there is no solution or there are infinitely many solutions");
        Matrix x = getAnswer(equationsSystem);
        long time = System.nanoTime() - start;
        Matrix discrepancy = getDiscrepancy(copy, x.getColumn(0));
        return new GaussResult(equationsSystem, x, discrepancy, newDeterminant, time);
    }

    private static void pivoting(Matrix equationsSystem, int col) throws ZeroColumnException {
        double[][] elements = equationsSystem.getElements();
        int maxAt = col;
        for (int i = col; i < equationsSystem.getRowsCount(); i++) {
            maxAt = Math.abs(elements[i][col]) > elements[maxAt][col] ? i : maxAt;
        }
        if(elements[maxAt][col] == 0)
            throw new ZeroColumnException("Column " + col + " is zero one");
        if(maxAt != col)
            equationsSystem.swapRows(maxAt, col);
    }

    private static void normalizationAndSubtract(Matrix equationsSystem, int k) {
        double[][] elements = equationsSystem.getElements();
        for(int i = k + 1; i < equationsSystem.getRowsCount(); i++) {
            double f = elements[i][k] / elements[k][k];
            for(int j = k + 1; j <= equationsSystem.getRowsCount(); j++) {
                elements[i][j] -= elements[k][j] * f;
            }
            elements[i][k] = 0;
        }
    }

    private static Matrix getAnswer(Matrix equationsSystem) {
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

    private static Matrix getDiscrepancy(Matrix initialSystem, double[] x) {
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
