package itmo.zavar.mathlab.lab.lab2.model.chord;

import itmo.zavar.mathlab.lab.lab2.model.FunctionWithDerivative;
import org.mariuszgromada.math.mxparser.Function;

public final class ChordMethod {

    public static ChordResult calculate(FunctionWithDerivative functionWithDerivative, double xPrev, double xCurr, double e) {
        double xNext = 0, temp;
        Function function = functionWithDerivative.getFunction();

        do {
            temp = xNext;
            xNext = xCurr - function.calculate(xCurr) * (xPrev - xCurr) / (function.calculate(xPrev) - function.calculate(xCurr));
            xPrev = xCurr;
            xCurr = temp;
        } while (Math.abs(xNext - xCurr) > e);


        return new ChordResult(functionWithDerivative, xNext);
    }

}
