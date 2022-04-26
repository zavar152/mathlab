package itmo.zavar.mathlab.lab.lab5.model;

import com.google.common.collect.Lists;
import itmo.zavar.mathlab.workspace.common.Result;
import org.mariuszgromada.math.mxparser.Function;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class ApproximationResult implements Result {

    private final Function apprFunction;

    public ApproximationResult(Function apprFunction) {
        this.apprFunction = apprFunction;
    }

    public Function getApprFunction() {
        return apprFunction;
    }

    @Override
    public ArrayList<Object> getRaw() {
        return Lists.newArrayList(apprFunction);
    }

    @Override
    public void print(OutputStream outputStream) {
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println("---Approximation---");
        printStream.println("Function: ");
        printStream.println(apprFunction.getFunctionExpressionString());
    }
}
