package itmo.zavar.mathlab.lab.lab4.model;

import com.google.common.collect.Lists;
import itmo.zavar.mathlab.lab.lab1.model.matrix.MatrixCreator;
import itmo.zavar.mathlab.workspace.common.Result;
import org.mariuszgromada.math.mxparser.Function;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class SplineResult implements Result {

    private final double[] x;
    private final double[] y;
    private final Function[] splines;

    public SplineResult(double[] x, double[] y, Function[] splines) {
        this.x = x;
        this.y = y;
        this.splines = splines;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public Function[] getSplines() {
        return splines;
    }

    @Override
    public ArrayList<Object> getRaw() {
        return Lists.newArrayList(x, y, splines);
    }

    @Override
    public void print(OutputStream outputStream) {
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println("---Spline method---");
        printStream.println("From dots: ");
        printStream.print("x: ");
        MatrixCreator.fromRow("x", x).print(printStream);
        printStream.print("y: ");
        MatrixCreator.fromRow("y", y).print(printStream);
        printStream.println("Splines: ");
        for (int i = 0; i < splines.length; i++) {
            printStream.print((i+1) + ": ");
            printStream.println(splines[i].getFunctionExpressionString());
        }
        printStream.println();
    }
}
