package itmo.zavar.mathlab.lab.lab1.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.lab.lab1.model.matrix.MatrixCreator;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

@Command(name = "randomMatrix", workspace = 1)
public class RandomMatrixCommand extends AbstractCommand {
    protected RandomMatrixCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if(args.length != 3)
            throw new CommandException("Enter a size of matrix and limits for generator");
        try {
            if(Double.parseDouble((String) args[1]) == Double.parseDouble((String) args[2]))
                throw new CommandException("");

            Matrix matrix = MatrixCreator.getRandomMatrixForGauss("matrix", Integer.parseInt((String) args[0]), Double.parseDouble((String) args[1]), Double.parseDouble((String) args[2]));
            ((PrintStream) outStream).println("Generated matrix:");
            matrix.print(outStream);
            workspace.put("matrix", matrix);
        } catch (ClassCastException e) {
            throw new CommandException("Invalid arguments");
        } catch (Exception e) {
            throw new CommandException("Failed to generate");
        }

    }

    @Override
    public String getHelp() {
        return "Generates a random matrix";
    }
}
