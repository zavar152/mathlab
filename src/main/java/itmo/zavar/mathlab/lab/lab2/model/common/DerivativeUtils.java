package itmo.zavar.mathlab.lab.lab2.model.common;

import org.mariuszgromada.math.mxparser.Function;

public final class DerivativeUtils {

    public static double getSecondDerivativeValue(Function f, double x, double eps) {
        double h = Math.sqrt(eps) * x;
        return (f.calculate(x + h) - 2 * f.calculate(x) + f.calculate(x - h)) / (h * h);
    }

    public static double getDerivativeValue(Function f, double x, double eps) {
        double h = Math.sqrt(eps) * x;
        return (f.calculate(x + h) - f.calculate(x))/h;
    }

}
