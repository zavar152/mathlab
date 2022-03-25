package itmo.zavar.mathlab.lab.lab3.model;

import com.google.common.collect.Lists;
import itmo.zavar.mathlab.workspace.common.Result;
import org.mariuszgromada.math.mxparser.Function;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public final class TrapezoidResult implements Result {

    public final double result;
    public final double n;
    public final double r;
    public final long time;
    public final Function function;

    public TrapezoidResult(double result, Function function, double n, double r, long time) {
        this.function = function;
        this.r = r;
        this.n = n;
        this.result = result;
        this.time = time;
    }

    public double getResult() {
        return result;
    }

    public double getN() {
        return n;
    }

    public double getR() {
        return r;
    }

    public Function getFunction() {
        return function;
    }

    @Override
    public ArrayList<Object> getRaw() {
        return Lists.newArrayList(result, n, r, function);
    }

    @Override
    public void print(OutputStream outputStream) {
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println("---Trapezoid method---");
        printStream.println("Function: ");
        printStream.println(function.getFunctionExpressionString());
        printStream.println("Answer: ");
        printStream.printf("%9.20f", result);
        printStream.println(" or " + result);
        printStream.println("N: ");
        printStream.println(n);
        printStream.println("R: ");
        printStream.printf("%9.20f", r);
        printStream.println(" or " + r);
        printStream.printf("Time (ns):\n%d%n", time);
        printStream.println();
    }
}
