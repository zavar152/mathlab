package itmo.zavar.mathlab.lab.lab1.model.gauss;

import com.sun.istack.internal.NotNull;
import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.lab.lab1.model.matrix.MatrixCreator;

import java.util.Arrays;
import java.util.Comparator;

public final class GaussAlgorithm {

    public static Matrix calculate(@NotNull Matrix equationsSystem) {
        pivoting(equationsSystem, 0);
        System.out.println(equationsSystem);
        pivoting(equationsSystem, 1);
        System.out.println(equationsSystem);

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

    private static void normalization(@NotNull Matrix equationsSystem) {

    }

}
