package itmo.zavar.mathlab.lab.lab2.model.tangent;

import itmo.zavar.mathlab.lab.lab2.model.SimpleFunction;
import itmo.zavar.mathlab.lab.lab2.model.common.DerivativeUtils;
import org.mariuszgromada.math.mxparser.Function;

public final class TangentMethod {

    public static TangentResult calculate(SimpleFunction simpleFunction, double x, double eps) {

        int iterations = 0;
        double y, dy;

        do {
            y = simpleFunction.getFunction().calculate(x);
            dy = DerivativeUtils.getDerivativeValue(simpleFunction.getFunction(), x, eps);
            x = x - y / dy;
            iterations++;
        } while (Math.abs(y) > eps);

        return new TangentResult(simpleFunction, x, iterations);
    }

}
