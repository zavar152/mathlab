package itmo.zavar.mathlab.lab.lab1.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab1.Lab1Workspace;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

@Command(name = "test", workspace = 1)
public class TestCommand extends AbstractCommand {

    protected TestCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        PrintStream ps = new PrintStream(outStream);
        ps.println("Hello world!");
    }

    @Override
    public String getHelp() {
        return "test command";
    }
}
