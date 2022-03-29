package itmo.zavar.mathlab.lab.lab3.model;

import itmo.zavar.mathlab.lab.lab2.model.common.DerivativeUtils;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import java.util.*;

public final class TrapezoidMethod {

    public static TrapezoidResult calculate(Expression aExp, Expression bExp, double eps, Function function) throws CalculationException {

        double result = 0;
        double epsDer = 0.0001;
        double a = aExp.calculate();
        double b = bExp.calculate();

        ArrayList<Double> fDerivative = new ArrayList<Double>();
        double oldI = 0;
        double newI = oldI + 2 * eps;

        if (a == b)
            return new TrapezoidResult(0, function, 0, 0, 0);

        long start, time;

        start = System.nanoTime();

        int n = 0;
        double length = 0;
        for (n = 1; Math.abs(newI-oldI) > eps; n *= 2) {
            oldI = newI;
            length = (b - a) / n;
            for (int i = 0; i < n; i++) {
                double xCurr = a + i * length;
                double xNext = a + (i + 1) * length;
                double temp;

                double xNextValue = function.calculate(xNext);
                double xCurrValue = function.calculate(xCurr);

                boolean isNextBad = Double.isNaN(xNextValue) || Double.isInfinite(xNextValue);
                boolean isCurrBad = Double.isNaN(xCurrValue) || Double.isInfinite(xCurrValue);
                if (isNextBad && isCurrBad) {
                    temp = (function.calculate(xNext + epsDer) + function.calculate(xNext - epsDer)) / 2;
                    fDerivative.add(Math.abs(DerivativeUtils.getSecondDerivativeValue(function, xNext + epsDer, 0.0001)));
                } else if (isNextBad) {
                    temp = (function.calculate(xNext + epsDer) + function.calculate(xNext - epsDer)) / 2;
                    fDerivative.add(Math.abs(DerivativeUtils.getSecondDerivativeValue(function, xNext + epsDer, 0.0001)));
                } else if (isCurrBad) {
                    temp = (function.calculate(xCurr + epsDer) + function.calculate(xCurr - epsDer)) / 2;
                    fDerivative.add(Math.abs(DerivativeUtils.getSecondDerivativeValue(function, xCurr + epsDer, 0.0001)));
                } else {
                    temp = function.calculate(xNext) + function.calculate(xCurr);
                    fDerivative.add(Math.abs(DerivativeUtils.getSecondDerivativeValue(function, xNext, 0.0001)));
                }
                result = result + temp;
            }
            result = result * (length / 2);
            newI = result;
        }

        time = System.nanoTime() - start;
        fDerivative.sort(Double::compare);
        double r = fDerivative.get(fDerivative.size() - 1) * n * Math.pow(length, 3) / 12;

        if (Math.abs(r) > Math.abs(result))
            throw new CalculationException("R is bigger than result, may be it's divergent integral");

        return new TrapezoidResult(result, function, n, r, time);
    }

}
