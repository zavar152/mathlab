package itmo.zavar.mathlab.lab.lab2.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;

@Command(name = "calculateEquation", workspace = 2)
public class CalculateEquationCommand extends AbstractCommand {
    protected CalculateEquationCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if (args.length != 4)
            throw new CommandException("Check input arguments");

        try {
            workspace.calculate(new Object[]{outStream, args[0], args[1], args[2], args[3]});
        } catch (ClassCastException | CalculationException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public String getHelp() {
        return "Calculates answer of nonlinear equations. Arguments: x0 a b eps";
    }
}
