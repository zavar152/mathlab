package itmo.zavar.mathlab.lab.lab3.model;

import itmo.zavar.mathlab.lab.lab2.model.common.DerivativeUtils;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import java.util.Arrays;

public final class TrapezoidMethod {

    public static TrapezoidResult calculate(Expression aExp, Expression bExp, int n, Function function) throws CalculationException{

        double result = 0;
        double eps = 0.0001;
        double a = aExp.calculate();
        double b = bExp.calculate();
        double length = (b - a) / n;
        double[] fDerivative = new double[n];

        if(a == b)
            return new TrapezoidResult(0, function, n, 0, 0);

        long start, time;

        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            double xCurr = a + i * length;
            double xNext = a + (i + 1) * length;
            double temp;

            double xNextValue = function.calculate(xNext);
            double xCurrValue = function.calculate(xCurr);

            boolean isNextBad = Double.isNaN(xNextValue) || Double.isInfinite(xNextValue);
            boolean isCurrBad = Double.isNaN(xCurrValue) || Double.isInfinite(xCurrValue);
            if(isNextBad && isCurrBad) {
                temp = (function.calculate(xNext + eps) + function.calculate(xNext - eps)) / 2;
                fDerivative[i] = Math.abs(DerivativeUtils.getSecondDerivativeValue(function, xNext + eps, 0.0001));
            } else if(isNextBad) {
                temp = (function.calculate(xNext + eps) + function.calculate(xNext - eps)) / 2;
                fDerivative[i] = Math.abs(DerivativeUtils.getSecondDerivativeValue(function, xNext + eps, 0.0001));
            } else if(isCurrBad) {
                temp = (function.calculate(xCurr + eps) + function.calculate(xCurr - eps)) / 2;
                fDerivative[i] = Math.abs(DerivativeUtils.getSecondDerivativeValue(function, xCurr + eps, 0.0001));
            } else {
                temp = function.calculate(xNext) + function.calculate(xCurr);
                fDerivative[i] = Math.abs(DerivativeUtils.getSecondDerivativeValue(function, xNext, 0.0001));
            }
            result = result + temp;

        }
        result = result * (length/2);
        time = System.nanoTime() - start;
        Arrays.sort(fDerivative);
        double r = fDerivative[fDerivative.length-1] * n * Math.pow(length, 3) / 12;

        if(r > result)
            throw new CalculationException("R is bigger than result, may be it's divergent integral");

        return new TrapezoidResult(result, function, n, r, time);
    }

}
