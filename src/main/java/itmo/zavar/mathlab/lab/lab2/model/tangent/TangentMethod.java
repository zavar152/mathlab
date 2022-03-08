package itmo.zavar.mathlab.lab.lab2.model.tangent;

import itmo.zavar.mathlab.lab.lab2.model.FunctionWithDerivative;

public final class TangentMethod {

    public static TangentResult calculate(FunctionWithDerivative functionWithDerivative, double x, double eps) {

        int iterations = 0;
        double y, dy;

        do {
            y = functionWithDerivative.getFunction().calculate(x);
            dy = functionWithDerivative.getDerivative().calculate(x);
            x = x - y / dy;
            iterations++;
        } while (Math.abs(y) > eps && iterations < 20000);

        return new TangentResult(functionWithDerivative, x, iterations);
    }
}
