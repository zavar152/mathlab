package itmo.zavar.mathlab.lab.lab2.model.iteration;

import org.mariuszgromada.math.mxparser.Function;

public final class SimpleIterationMethod {

    public static SimpleIterationResult calculation(Function[] functions, double eps) {

        double[] xPrev = new double[functions.length];
        double[] xNext = new double[functions.length];
        double[] discrepancy = new double[functions.length];
        long start, time = 0;

        int c;

        start = System.nanoTime();
        do {
            c = 0;
            for (int i = 0; i < functions.length; i++)
                xNext[i] = functions[i].calculate(xPrev);

            for (int i = 0; i < functions.length; i++) {
                if (Math.abs(xPrev[i] - xNext[i]) <= eps)
                    c++;
                else
                    break;
            }
            System.arraycopy(xNext, 0, xPrev, 0, xNext.length);
        } while (c != functions.length);
        time = System.nanoTime() - start;
        for (int i = 0; i < functions.length; i++) {
            discrepancy[i] = xNext[i] - functions[i].calculate(xNext);
        }

        return new SimpleIterationResult(functions, eps, xNext, discrepancy, time);
    }

}
