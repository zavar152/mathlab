package itmo.zavar.mathlab.lab.lab2;

import itmo.zavar.mathlab.annotation.Workspace;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.lab.lab1.exception.GaussException;
import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.lab.lab2.model.FunctionWithDerivative;
import itmo.zavar.mathlab.lab.lab2.model.chord.ChordMethod;
import itmo.zavar.mathlab.lab.lab2.model.chord.ChordResult;
import itmo.zavar.mathlab.lab.lab2.model.common.ChordAndTangentResult;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.lab.lab2.model.tangent.TangentMethod;
import itmo.zavar.mathlab.lab.lab2.model.tangent.TangentResult;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.OutputStream;
import java.util.HashMap;

@Workspace(name = "lab2", id = 2)
public class Lab2Workspace extends AbstractWorkspace {

    protected Lab2Workspace(HashMap<String, AbstractCommand> commandMap, String name, int id) {
        super(commandMap, name, id);
    }

    @Override
    public void calculate(Object[] args) throws CalculationException {
        if (args.length != 1)
            throw new CalculationException("Check input arguments");

        if (workspaceContainer.size() == 0)
            throw new CalculationException("No elements in workspace");

        FunctionWithDerivative functionWithDerivative;
        try {
            functionWithDerivative = (FunctionWithDerivative) workspaceContainer.get("enteredFunction");
        } catch (ClassCastException e) {
            throw new CalculationException("Function not found");
        }

        TangentResult tangentResult = TangentMethod.calculate(functionWithDerivative, 1, 0.001);
        ChordResult chordResult = ChordMethod.calculate(functionWithDerivative, 1, 10, 0.001);

        lastResult = new ChordAndTangentResult(chordResult, tangentResult);
        lastResult.print((OutputStream) args[0]);
    }
}
