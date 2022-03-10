package itmo.zavar.mathlab.lab.lab2.model.chord;

import com.google.common.collect.Lists;
import itmo.zavar.mathlab.lab.lab2.model.SimpleFunction;
import itmo.zavar.mathlab.workspace.common.Result;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public final class ChordResult implements Result {

    private final SimpleFunction function;
    private final double answer;
    private final int iterations;

    public ChordResult(SimpleFunction function, double answer, int iterations) {
        this.function = function;
        this.answer = answer;
        this.iterations = iterations;
    }

    public int getIterations() {
        return iterations;
    }

    public SimpleFunction getFunction() {
        return function;
    }

    public double getAnswer() {
        return answer;
    }

    @Override
    public ArrayList<Object> getRaw() {
        return Lists.newArrayList(function, answer);
    }

    @Override
    public void print(OutputStream outputStream) {
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println("---Chord method---");
        printStream.println("Function: ");
        printStream.println(function.getFunction().getFunctionExpressionString());
        printStream.println();
        printStream.println("Answer: ");
        printStream.printf("%9.20f", answer);
        printStream.println(" or " + answer);
        printStream.println();
        printStream.println("Iterations: ");
        printStream.println(iterations);
        printStream.println();
    }
}
