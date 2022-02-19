package itmo.zavar.mathlab.command.general;

import itmo.zavar.mathlab.annotation.CommandGeneral;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

@CommandGeneral(name = "info")
public class InfoCommand extends AbstractCommand {
    protected InfoCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        PrintStream outPrinter = new PrintStream(outStream);
        outPrinter.println("Workspace name/id: " + workspace.getName() + "/" + workspace.getId());
        outPrinter.println("Workspace container (key - class):");
        workspace.getWorkspaceContainer().forEach((s, mathObject) -> {
            System.out.println(s + " - " + mathObject.getClass().getName());
        });
    }

    @Override
    public String getHelp() {
        return "Shows info about workspace and its container";
    }
}
