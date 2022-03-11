package itmo.zavar.mathlab.lab.lab2.model;

import itmo.zavar.mathlab.workspace.common.MathObject;
import org.mariuszgromada.math.mxparser.Function;

public final class EquationSystem implements MathObject {

    private final String name;
    private final Function[] functions;

    public EquationSystem(String name, Function[] functions) {
        this.name = name;
        this.functions = functions;
    }

    public Function[] getFunctions() {
        return functions;
    }

    @Override
    public String name() {
        return name;
    }
}
