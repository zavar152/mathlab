package itmo.zavar.mathlab.lab.lab1.model.gauss;

import com.sun.istack.internal.NotNull;
import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.lab.lab1.model.matrix.MatrixCreator;

import java.util.Arrays;
import java.util.Comparator;

public final class GaussAlgorithm {

    public static Matrix calculate(@NotNull Matrix equationsSystem) {
        for(int i = 0; i < equationsSystem.getRowsCount() - 1; i++) {
            pivoting(equationsSystem, i);
            System.out.println(equationsSystem);
            normalization(equationsSystem, i);
            System.out.println(equationsSystem);
            subtract(equationsSystem, i);
            System.out.println(equationsSystem);
        }
        System.out.println(Arrays.toString(getAnswer(equationsSystem)));
        return null;
    }

    private static void pivoting(@NotNull Matrix equationsSystem, int col) {
        double[][] copy;
        if(col == 0) {
            copy = equationsSystem.getCopyElements();
        } else {
            copy = equationsSystem.getWithoutRow(col - 1).getCopyElements();
        }
        Arrays.sort(copy, (entry1, entry2) -> -Double.compare(entry1[col], entry2[col]));
        if (equationsSystem.getRowsCount() - 1 >= 0)
            System.arraycopy(copy, 0, equationsSystem.getElements(), col, equationsSystem.getRowsCount() - 1 + col - col);
    }

    private static void normalization(@NotNull Matrix equationsSystem, int col) {
        for(int i = 0; i < equationsSystem.getRowsCount(); i++) {
            double k = equationsSystem.get(i, col);
            for(int j = 0; j < equationsSystem.getColumnsCount(); j++) {
                equationsSystem.set(equationsSystem.get(i, j)/k, i, j);
            }
        }
    }

    private static void subtract(@NotNull Matrix equationsSystem, int r) {
        double[] row = equationsSystem.getRow(r);
        int cols = equationsSystem.getColumnsCount();
        for(int i = 0; i < equationsSystem.order(); i++) {
            if(i / cols == r)
                continue;
            equationsSystem.getElements()[i / cols][i % cols] = equationsSystem.getElements()[i / cols][i % cols] - row[i % cols];
        }
    }

    private static double[] getAnswer(@NotNull Matrix equationsSystem) {
        double[][] elements = equationsSystem.getElements();
        double[] x = new double[equationsSystem.getRowsCount()];
        for(int i = equationsSystem.getRowsCount() - 1; i >= 0; i--) {
            x[i] = elements[i][equationsSystem.getRowsCount()];
            for(int j = i + 1; j < equationsSystem.getRowsCount(); j++) {
                x[i] -= elements[i][j] * x[j];
            }
            x[i] = x[i]/elements[i][i];
        }
        return x;
    }

}
