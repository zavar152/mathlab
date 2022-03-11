package itmo.zavar.mathlab.lab.lab2.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab2.model.EquationSystem;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.mariuszgromada.math.mxparser.Function;

import java.io.InputStream;
import java.io.OutputStream;

@Command(name = "calculateSystem", workspace = 2)
public class CalculateSystemCommand extends AbstractCommand {

    protected CalculateSystemCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if (args.length != 1)
            throw new CommandException("Check input arguments");

        try {
            workspace.calculate(new Object[]{"s", outStream, args[0]});
        } catch (ClassCastException | CalculationException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public String getHelp() {
        return "Calculates answer of system of nonlinear equations. Arguments: eps";
    }
}
