package itmo.zavar.mathlab.lab.lab2.model;

import itmo.zavar.mathlab.workspace.common.MathObject;
import org.mariuszgromada.math.mxparser.Function;

public final class FunctionWithDerivative implements MathObject {

    private final Function derivative;
    private final Function function;
    private final String name;

    public FunctionWithDerivative(String name, Function function, Function derivative) {
        this.function = function;
        this.derivative = derivative;
        this.name = name;
    }

    public Function getFunction() {
        return function;
    }

    public Function getDerivative() {
        return derivative;
    }

    @Override
    public String name() {
        return name;
    }
}
