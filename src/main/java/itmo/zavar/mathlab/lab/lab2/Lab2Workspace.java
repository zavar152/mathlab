package itmo.zavar.mathlab.lab.lab2;

import itmo.zavar.mathlab.annotation.Workspace;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.lab.lab2.model.SimpleFunction;
import itmo.zavar.mathlab.lab.lab2.model.chord.ChordMethod;
import itmo.zavar.mathlab.lab.lab2.model.chord.ChordResult;
import itmo.zavar.mathlab.lab.lab2.model.common.ChordAndTangentResult;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.lab.lab2.model.tangent.TangentMethod;
import itmo.zavar.mathlab.lab.lab2.model.tangent.TangentResult;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

@Workspace(name = "lab2", id = 2)
public class Lab2Workspace extends AbstractWorkspace {

    protected Lab2Workspace(HashMap<String, AbstractCommand> commandMap, String name, int id) {
        super(commandMap, name, id);
    }

    @Override
    public void calculate(Object[] args) throws CalculationException {
        if (args.length != 5)
            throw new CalculationException("Check input arguments");

        if (workspaceContainer.size() == 0)
            throw new CalculationException("No elements in workspace");

        SimpleFunction simpleFunction;
        try {
            simpleFunction = (SimpleFunction) workspaceContainer.get("enteredFunction");
        } catch (ClassCastException e) {
            throw new CalculationException("Function not found");
        }

        double x0 = Double.parseDouble((String) args[1]);
        double a = Double.parseDouble((String) args[2]);
        double b = Double.parseDouble((String) args[3]);
        double eps = Double.parseDouble((String) args[4]);

        if(x0 < a || x0 > b)
            throw new CalculationException("x0 is out of bounds");

        if(simpleFunction.getFunction().calculate(a) * simpleFunction.getFunction().calculate(b) >= 0)
            throw new CalculationException("There is the same sign at the bounds or root is 0");

        PrintStream printStream = new PrintStream((OutputStream) args[0]);

        printStream.println("Calculating with tangent method...\n");
        TangentResult tangentResult = TangentMethod.calculate(simpleFunction, x0, eps);
        tangentResult.print((OutputStream) args[0]);
        printStream.println("Calculating with chord method...\n");
        ChordResult chordResult = ChordMethod.calculate(simpleFunction, a, b, eps);
        chordResult.print((OutputStream) args[0]);

        printStream.println("Difference: ");
        double dif = Math.abs(tangentResult.getAnswer() - chordResult.getAnswer());
        printStream.printf("%9.20f", dif);
        printStream.println(" or " + dif);
        printStream.println();

        lastResult = new ChordAndTangentResult(chordResult, tangentResult);
    }
}
