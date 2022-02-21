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
    private final double initialDeterminant;
    private final double newDeterminant;
    private final long time;

    public GaussResult(Matrix newSystem, Matrix x, Matrix discrepancy, double initialDeterminant, double newDeterminant, long time) {
        this.x = x;
        this.discrepancy = discrepancy;
        this.newSystem = newSystem;
        this.initialDeterminant = initialDeterminant;
        this.newDeterminant = newDeterminant;
        this.time = time;
    }

    @Override
    public List<Object> getRaw() {
        return Arrays.asList(x, discrepancy, newSystem, initialDeterminant, newDeterminant, time);
    }

    @Override
    public void print(OutputStream outputStream) {
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println("Answer: ");
        printStream.println(x);
        printStream.println("Discrepancy: ");
        printStream.println(discrepancy);
        printStream.println("Triangle matrix: ");
        printStream.println(newSystem);
        printStream.println("Determinant of initial matrix: ");
        printStream.printf("%1.3f", initialDeterminant);
        printStream.println();
        printStream.println("Determinant of triangular matrix: ");
        printStream.printf("%1.3f", newDeterminant);
        printStream.println();
        printStream.println("Execution time (ns): ");
        printStream.println(time);
    }
}
