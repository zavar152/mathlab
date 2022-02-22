package itmo.zavar.mathlab.lab.lab1.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab1.exception.GaussException;
import itmo.zavar.mathlab.lab.lab1.model.gauss.GaussAlgorithm;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;

@Command(name = "gauss", workspace = 1)
public class GaussCommand extends AbstractCommand {
    protected GaussCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if(args.length != 1)
            throw new CommandException("Enter a pivoting parameter (boolean)");
        try {
            workspace.calculate(new Object[]{args[0], outStream});
        } catch (GaussException | ClassCastException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public String getHelp() {
        return null;
    }
}
