package itmo.zavar.mathlab.lab.lab5.model;

import org.apache.commons.math3.linear.*;
import org.mariuszgromada.math.mxparser.Function;

public class ApproximationMethod {

    public static ApproximationResult calculate(double[] x, double[] y) {
        double xSum = 0, ySum = 0, xSquareSum = 0, xMulYSum = 0;

        for (int i = 0; i < x.length; i++) {
            xSum += x[i];
            ySum += y[i];
            xSquareSum += (x[i] * x[i]);
            xMulYSum += x[i] * y[i];
        }

        RealVector b = new ArrayRealVector(new double[]{xMulYSum, ySum}, false);
        RealMatrix a = new Array2DRowRealMatrix(new double[][]{{xSquareSum, xSum}, {xSum, x.length}}, false);
        DecompositionSolver solver = new LUDecomposition(a).getSolver();
        RealVector solution = solver.solve(b);

        Function appFunction = new Function("f(x)=" + solution.getEntry(0) + "*x+" + solution.getEntry(1));

        return new ApproximationResult(appFunction);
    }

}
