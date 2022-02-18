package itmo.zavar.mathlab.command.general;

import itmo.zavar.mathlab.annotation.CommandGeneral;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;

@CommandGeneral(name = "exit")
public class ExitCommand extends AbstractCommand {

    protected ExitCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        workspace.requiredShutdown();
    }

    @Override
    public String getHelp() {
        return "Stops the program";
    }
}
