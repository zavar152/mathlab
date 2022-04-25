package itmo.zavar.mathlab.lab.lab5;

import com.google.common.collect.Lists;
import itmo.zavar.mathlab.lab.lab1.model.matrix.MatrixCreator;
import itmo.zavar.mathlab.workspace.common.Result;
import org.mariuszgromada.math.mxparser.Function;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class AdvancedEulerResult implements Result {

    private final Function function;
    private final Function realFunction;
    private final double b;
    private final int dotsNumber;
    private final double[] x;
    private final double[] y;
    private final double[] realY;
    public final long time;

    public AdvancedEulerResult(Function function, Function realFunction, double b, int dotsNumber, double[] x, double[] y, double[] realY, long time) {
        this.function = function;
        this.realFunction = realFunction;
        this.b = b;
        this.dotsNumber = dotsNumber;
        this.x = x;
        this.y = y;
        this.realY = realY;
        this.time = time;
    }

    public Function getFunction() {
        return function;
    }

    public Function getRealFunction() {
        return realFunction;
    }

    public double getB() {
        return b;
    }

    public int getDotsNumber() {
        return dotsNumber;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public double[] getRealY() {
        return realY;
    }

    public long getTime() {
        return time;
    }

    @Override
    public ArrayList<Object> getRaw() {
        return Lists.newArrayList(function, realFunction, b, dotsNumber, x, y, realY, time);
    }

    @Override
    public void print(OutputStream outputStream) {
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println("---Advanced Euler method---");
        printStream.println("Function: ");
        printStream.println(function.getFunctionExpressionString());
        printStream.println("Real function: ");
        printStream.println(realFunction.getFunctionExpressionString());
        printStream.println("Limits: [" + x[0] + "; " + b + "]");
        printStream.println("Calculated dots: ");
        printStream.print("x: ");
        MatrixCreator.fromRow("x", x).print(printStream);
        printStream.print("y: ");
        MatrixCreator.fromRow("y", y).print(printStream);
        printStream.println("Calculated real dots: ");
        printStream.print("x: ");
        MatrixCreator.fromRow("x", x).print(printStream);
        printStream.print("y: ");
        MatrixCreator.fromRow("realY", realY).print(printStream);
        printStream.printf("Time (ns):\n%d%n", time);
        printStream.println();
    }
}
