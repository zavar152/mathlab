package itmo.zavar.mathlab.lab.lab1.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;

@Command(name = "showMatrix", workspace = 1)
public class ShowMatrixCommand extends AbstractCommand {
    protected ShowMatrixCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if(workspace.size() < 1)
            throw new CommandException("Workspace is empty");

        try {
            ((Matrix) workspace.get("matrix")).print(outStream);
        } catch (ClassCastException e) {
            throw new CommandException("Cast failed");
        }
    }

    @Override
    public String getHelp() {
        return "Shows the matrix from workspace";
    }
}
