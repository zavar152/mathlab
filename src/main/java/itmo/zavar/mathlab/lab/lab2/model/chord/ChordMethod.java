package itmo.zavar.mathlab.lab.lab2.model.chord;

import itmo.zavar.mathlab.lab.lab2.model.FunctionWithDerivative;
import org.mariuszgromada.math.mxparser.Function;

public final class ChordMethod {

    public static ChordResult calculate(FunctionWithDerivative functionWithDerivative, double x0, double x1, double e) {
        double result = x1;
        double f0, f;
        int iterations = 0;
        Function function = functionWithDerivative.getFunction();

        do {
            f = function.calculate(result)/result;
            f0 = function.calculate(x0)/x0;
            result = result - f / (f - f0)*(result - x0);
            iterations++;
        } while (Math.abs(f) > e && iterations < 20000);


        return new ChordResult(functionWithDerivative, result, iterations);
    }

}
