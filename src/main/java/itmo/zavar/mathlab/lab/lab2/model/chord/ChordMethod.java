package itmo.zavar.mathlab.lab.lab2.model.chord;

import itmo.zavar.mathlab.lab.lab2.model.SimpleFunction;
import itmo.zavar.mathlab.lab.lab2.model.common.DerivativeUtils;
import org.mariuszgromada.math.mxparser.Function;

public final class ChordMethod {

    public static ChordResult calculate(SimpleFunction simpleFunction, double a, double b, double e) {
        double fA, fB, f;
        int iterations = 0;
        Function function = simpleFunction.getFunction();

        double x = a - (function.calculate(a) / (function.calculate(b) - function.calculate(a))) * (b - a);
        double secDerValue = DerivativeUtils.getSecondDerivativeValue(function, x, e);

        long start, time = 0;
        if (secDerValue * function.calculate(a) > 0) {
            x = b;
            start = System.nanoTime();
            do {
                f = function.calculate(x);
                fA = function.calculate(a);
                x = x - (f / (f - fA)) * (x - a);
                iterations++;
            } while (Math.abs(f) > e);
            time = System.nanoTime() - start;
        } else if (secDerValue * function.calculate(b) > 0) {
            x = a;
            start = System.nanoTime();
            do {
                f = function.calculate(x);
                fB = function.calculate(b);
                x = x - (f / (fB - x)) * (b - x);
                iterations++;
            } while (Math.abs(f) > e);
            time = System.nanoTime() - start;
        }


        return new ChordResult(simpleFunction, x, iterations, time, 0 - function.calculate(x));
    }

}
