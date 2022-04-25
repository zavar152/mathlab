package itmo.zavar.mathlab.lab.lab5;

import org.mariuszgromada.math.mxparser.Function;

public class AdvancedEulerMethod {

    public static AdvancedEulerResult calculate(double y0, double x0, double b, int dotsNumber, Function function, Function realFunction) {
        double h = (b - x0) / dotsNumber;
        double[] x = new double[dotsNumber+1];
        double[] y = new double[dotsNumber+1];
        double[] realY = new double[dotsNumber+1];

        x[0] = x0;
        y[0] = y0;
        realY[0] = y0;

        long start, time;

        start = System.nanoTime();
        for (int i = 0; i < dotsNumber - 1; i++) {
            y[i+1] = y[i] + h * function.calculate(x[i] + h/2, y[i] + h/2*function.calculate(x[i], y[i]));
            x[i+1] = x[i] + h;
            realY[i] = realFunction.calculate(x[i]);
        }
        y[dotsNumber] = y[dotsNumber-1] + h * function.calculate(x[dotsNumber-1] + h/2, y[dotsNumber-1] + h/2*function.calculate(x[dotsNumber-1], y[dotsNumber-1]));
        x[dotsNumber] = x[dotsNumber-1] + h;
        realY[dotsNumber-1] = realFunction.calculate(x[dotsNumber-1]);
        realY[dotsNumber] = realFunction.calculate(x[dotsNumber]);
        time = System.nanoTime() - start;

        return new AdvancedEulerResult(function, realFunction, b, dotsNumber, x, y, realY, time);
    }

}
