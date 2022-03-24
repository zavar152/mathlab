package itmo.zavar.mathlab.lab.lab2.model.tangent;

import itmo.zavar.mathlab.lab.lab2.model.SimpleFunction;
import itmo.zavar.mathlab.lab.lab2.model.common.DerivativeUtils;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import org.mariuszgromada.math.mxparser.Function;

public final class TangentMethod {

    public static TangentResult calculate(SimpleFunction simpleFunction, double x, double eps) {

        int iterations = 0;
        double y, dy;
        Function function = simpleFunction.getFunction();

        long start, time = 0;
        start = System.nanoTime();
        do {
            y = function.calculate(x);
            dy = DerivativeUtils.getDerivativeValue(function, x, eps);
            x = x - y / dy;
            iterations++;
        } while (Math.abs(y) > eps);
        time = System.nanoTime() - start;

        return new TangentResult(simpleFunction, x, iterations, time, 0 - function.calculate(x));
    }

}
