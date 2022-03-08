package itmo.zavar.mathlab.lab.lab2.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab1.exception.GaussException;
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
        try {
            workspace.calculate(new Object[]{outStream});
        } catch (ClassCastException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public String getHelp() {
        return "Calculates answer of nonlinear equations";
    }
}
