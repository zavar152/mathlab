package itmo.zavar.mathlab.lab.lab2.model;

import itmo.zavar.mathlab.workspace.common.MathObject;
import org.mariuszgromada.math.mxparser.Function;

public final class SimpleFunction implements MathObject {

    private final Function function;
    private final String name;

    public SimpleFunction(String name, Function function) {
        this.function = function;
        this.name = name;
    }

    public Function getFunction() {
        return function;
    }

    @Override
    public String name() {
        return name;
    }
}
