package itmo.zavar.mathlab.lab.lab2.model.chord;

import itmo.zavar.mathlab.lab.lab2.model.SimpleFunction;
import itmo.zavar.mathlab.lab.lab2.model.common.DerivativeUtils;
import org.mariuszgromada.math.mxparser.Function;

public final class ChordMethod {

    public static ChordResult calculate(SimpleFunction simpleFunction, double a, double b, double e) {
        double fA, fB, f, x = 0;
        int iterations = 0;
        Function function = simpleFunction.getFunction();

        long start, time = 0;

        start = System.nanoTime();
        while (true) {
            fA = function.calculate(a);
            fB = function.calculate(b);
            x = a - fA * (b - a) / (fB - fA);
            f = function.calculate(x);
            if (Math.abs(f) > e) {
                if (fA * f > 0)
                    a = x;
                else
                    b = x;
            } else {
                break;
            }
            iterations++;
        }
        time = System.nanoTime() - start;

        return new ChordResult(simpleFunction, x, iterations, time, 0 - function.calculate(x));
    }

}
