package itmo.zavar.mathlab.lab.lab1.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Command(name = "fromFile", workspace = 1)
public class FromFileCommand extends AbstractCommand {
    protected FromFileCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if (args.length != 1)
            throw new CommandException("Enter a file path");

        Path path = Paths.get((String) args[0]);

        if (!Files.isReadable(path) || Files.isDirectory(path) || !Files.exists(path))
            throw new CommandException("Check your path");

        String[] fromFile;

        try {
            fromFile = Files.lines(path).toArray(String[]::new);
        } catch (IOException e) {
            throw new CommandException("Reading failed");
        }

        int size = Integer.parseInt(fromFile[0]);
        PrintStream printStream = new PrintStream(outStream);
        double[][] elements = new double[size][size + 1];

        for (int i = 1; i <= size; i++) {
            try {
                double[] temp = Arrays.stream(fromFile[i].split(" ")).mapToDouble(Double::parseDouble).toArray();
                if (temp.length != size + 1) {
                    throw new CommandException("Invalid number of elements");
                }
                elements[i - 1] = temp;
            } catch (Exception e) {
                throw new CommandException("Error at " + i + " column: " + e.getMessage());
            }
        }

        try {
            workspace.put("matrix", new Matrix("Matrix", elements));
        } catch (IllegalArgumentException e) {
            throw new CommandException("Invalid input");
        }
        printStream.println("Recognized matrix:");
        workspace.executeCommand("showMatrix", new Object[]{}, inStream, outStream, errStream);
    }

    @Override
    public String getHelp() {
        return "Loads matrix from file";
    }
}
