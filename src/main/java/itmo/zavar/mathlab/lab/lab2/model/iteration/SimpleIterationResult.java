package itmo.zavar.mathlab.lab.lab2.model.iteration;

import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.workspace.common.Result;
import org.mariuszgromada.math.mxparser.Function;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public final class SimpleIterationResult implements Result {

    private final Function[] functions;
    private final double eps;
    private final long time;
    private final double[] discrepancy;
    private final double[] answer;

    public SimpleIterationResult(Function[] functions, double eps, double[] answer, double[] discrepancy, long time) {
        this.functions = functions;
        this.eps = eps;
        this.discrepancy = discrepancy;
        this.answer = answer;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public double[] getAnswer() {
        return answer;
    }

    public Function[] getFunctions() {
        return functions;
    }

    public double getEps() {
        return eps;
    }

    public double[] getDiscrepancy() {
        return discrepancy;
    }

    @Override
    public List<Object> getRaw() {
        return Arrays.asList(functions, eps, answer, discrepancy);
    }

    @Override
    public void print(OutputStream outputStream) {
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println("System: ");
        Arrays.stream(functions).forEach(function -> printStream.println(function.getFunctionExpressionString()));
        printStream.println();
        printStream.println("Eps: ");
        printStream.println(eps);
        printStream.println();
        printStream.println("Execution time (ns): ");
        printStream.println(time);
        printStream.println();
        printStream.println("Answer and discrepancy (index: [ans] [dis]):");
        for (int i = 0; i < answer.length; i++) {
            printStream.printf("%3d", (i + 1));
            printStream.print(":   [");
            printStream.printf("%9.20f", answer[i]);
            printStream.print("]   ");
            printStream.print("[");
            printStream.printf("%9.20f", discrepancy[i]);
            printStream.println("]");
        }
    }
}
