package itmo.zavar.mathlab.command.general;

import itmo.zavar.mathlab.annotation.CommandGeneral;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

@CommandGeneral(name = "lastResult")
public class LastResultCommand extends AbstractCommand {
    protected LastResultCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if(workspace.getLastResult().isPresent())
            workspace.getLastResult().ifPresent(result -> result.print(outStream));
        else
            ((PrintStream) outStream).println("There is no any results");
    }

    @Override
    public String getHelp() {
        return "Shows last result if its exists";
    }
}
