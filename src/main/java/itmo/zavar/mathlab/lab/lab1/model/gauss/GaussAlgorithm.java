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
        int cols = equationsSystem.getColumnsCount();
        for (int i = 0; i < equationsSystem.order(); i++) {
            double k = equationsSystem.get(i / cols, col);
            equationsSystem.set(equationsSystem.get(i / cols, i % cols)/k, i / cols, i % cols);
        }
    }

    private static void subtract(@NotNull Matrix equationsSystem, int r) {
        double[] row = equationsSystem.getRow(r);
        for(int i = 0; i < equationsSystem.getRowsCount(); i++) {
            if(i != r) {
                for(int j = 0; j < equationsSystem.getColumnsCount(); j++) {
                    equationsSystem.getElements()[i][j] = equationsSystem.getElements()[i][j] - row[j];
                }
            }
        }
    }

}
