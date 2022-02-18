package itmo.zavar.mathlab.command;

import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractCommand {

    private final String name;

    protected AbstractCommand(String name) {
        this.name = name;
    }

    public abstract void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException;

    public abstract String getHelp();

    public final String getName() {
        return name;
    }
}
