package itmo.zavar.mathlab.lab.lab3;

import itmo.zavar.mathlab.annotation.Workspace;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.lab.lab2.model.SimpleFunction;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.lab.lab3.model.TrapezoidMethod;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

@Workspace(name = "lab3", id = 3)
public class Lab3Workspace extends AbstractWorkspace {

    protected Lab3Workspace(HashMap<String, AbstractCommand> commandMap, String name, int id) {
        super(commandMap, name, id);
    }

    @Override
    public void calculate(Object[] args) throws CalculationException {
        if (workspaceContainer.size() == 0)
            throw new CalculationException("No elements in workspace");

        if (args.length != 4)
            throw new CalculationException("Check input arguments");

        SimpleFunction simpleFunction;
        try {
            simpleFunction = (SimpleFunction) workspaceContainer.get("integral");
        } catch (ClassCastException e) {
            throw new CalculationException("Integral not found");
        }

        Expression aExp = new Expression((String) args[1]);
        Expression bExp = new Expression((String) args[2]);
        int n = Integer.parseInt((String) args[3]);

        lastResult = TrapezoidMethod.calculate(aExp, bExp, n, simpleFunction.getFunction());
        lastResult.print((OutputStream) args[0]);
    }
}
