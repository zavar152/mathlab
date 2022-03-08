package itmo.zavar.mathlab.lab.lab2.model.chord;

import com.google.common.collect.Lists;
import itmo.zavar.mathlab.lab.lab2.model.FunctionWithDerivative;
import itmo.zavar.mathlab.workspace.common.Result;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public final class ChordResult implements Result {

    private final FunctionWithDerivative function;
    private final double answer;

    public ChordResult(FunctionWithDerivative function, double answer) {
        this.function = function;
        this.answer = answer;
    }

    public FunctionWithDerivative getFunction() {
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
        printStream.println("Function: ");
        printStream.println(function.getFunction().getFunctionExpressionString());
        printStream.println();
        printStream.println("Answer: ");
        printStream.println(answer);
    }
}
