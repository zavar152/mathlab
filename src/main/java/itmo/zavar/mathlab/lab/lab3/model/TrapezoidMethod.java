package itmo.zavar.mathlab.lab.lab3.model;

import itmo.zavar.mathlab.lab.lab2.model.common.DerivativeUtils;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import java.util.Arrays;

public final class TrapezoidMethod {

    public static TrapezoidResult calculate(Expression aExp, Expression bExp, int n, Function function) {

        double result = 0;
        double a = aExp.calculate();
        double b = bExp.calculate();
        double length = (b - a) / n;
        double[] fDerivative = new double[n];

        long start, time = 0;

        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            double xCurr = a + i * length;
            double xNext = a + (i + 1) * length;

            result = result + (xNext - xCurr) * (function.calculate(xNext) + function.calculate(xCurr)) / 2;
            fDerivative[i] = Math.abs(DerivativeUtils.getSecondDerivativeValue(function, xNext, 0.0001));

        }
        time = System.nanoTime() - start;
        Arrays.sort(fDerivative);
        double r = fDerivative[fDerivative.length-1] * n * Math.pow(length, 3) / 12;

        return new TrapezoidResult(result, function, n, r, time);
    }

}
