package itmo.zavar.mathlab.command.general;

import itmo.zavar.mathlab.annotation.CommandGeneral;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab1.Lab1Workspace;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

@CommandGeneral(name = "help")
public class HelpCommand extends AbstractCommand {

    protected HelpCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        PrintStream printStream = new PrintStream(outStream);
        workspace.getCommandMap().forEach((s, abstractCommand) -> {
            printStream.println(abstractCommand.getName() + ": " + abstractCommand.getHelp());
        });
    }

    @Override
    public String getHelp() {
        return "Shows description of every command";
    }


}
