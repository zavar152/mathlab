package itmo.zavar.mathlab.lab.lab1.model.gauss;

import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.workspace.common.Result;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public final class GaussResult implements Result {

    private final Matrix x;
    private final Matrix discrepancy;
    private final Matrix newSystem;
    private final double newDeterminant;
    private final long time;

    public GaussResult(Matrix newSystem, Matrix x, Matrix discrepancy, double newDeterminant, long time) {
        this.x = x;
        this.discrepancy = discrepancy;
        this.newSystem = newSystem;
        this.newDeterminant = newDeterminant;
        this.time = time;
    }

    public Matrix getX() {
        return x;
    }

    public Matrix getDiscrepancy() {
        return discrepancy;
    }

    public Matrix getNewSystem() {
        return newSystem;
    }

    public double getNewDeterminant() {
        return newDeterminant;
    }

    public long getTime() {
        return time;
    }

    @Override
    public List<Object> getRaw() {
        return Arrays.asList(x, discrepancy, newSystem, newDeterminant, time);
    }

    @Override
    public void print(OutputStream outputStream) {
        PrintStream printStream = new PrintStream(outputStream);
        printAnswer(x, discrepancy, printStream);
        printStream.println();
        printStream.println("Triangle matrix: ");
        printStream.println(newSystem);
        printStream.println("Determinant of triangular matrix: ");
        printStream.printf("%1.3f", newDeterminant);
        printStream.println();
        printStream.println("Execution time (ns): ");
        printStream.println(time);
    }

    private void printAnswer(Matrix x, Matrix discrepancy, PrintStream printStream) {
        printStream.println("Answer and discrepancy (index: [ans] [dis]):");
        for (int i = 0; i < x.getRowsCount(); i++) {
            printStream.printf("%3d", (i + 1));
            printStream.print(":   [");
            printStream.printf("%9.20f", x.getElements()[i][0]);
            printStream.print("]   ");
            printStream.print("[");
            printStream.printf("%9.20f", discrepancy.getElements()[i][0]);
            printStream.println("]");
        }
    }
}
