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
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Command(name = "enterMatrix", workspace = 1)
public class EnterMatrixCommand extends AbstractCommand {
    protected EnterMatrixCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if(args.length != 1)
            throw new CommandException("Enter a size");

        int size;
        try {
            size = Integer.parseInt((String) args[0]);
        } catch (NumberFormatException e) {
            throw new CommandException("Enter a number");
        }

        Scanner scanner = new Scanner(System.in);
        PrintStream printStream = new PrintStream(outStream);
        double[][] elements = new double[size][size + 1];

        for(int i = 1; i <= size; i++) {
            try {
                printStream.print("Column #" + i + ": ");
                String r = scanner.nextLine();
                double[] temp = Arrays.stream(r.split(" ")).mapToDouble(Double::parseDouble).toArray();
                if(temp.length != size + 1) {
                    printStream.println("Invalid number of elements");
                    i--;
                    continue;
                }
                elements[i - 1] = temp;
            } catch (NoSuchElementException e) {
              throw new CommandException("");
            } catch (Exception e) {
                printStream.println("Error: " + e.getMessage());
                i--;
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
        return "Read matrix from console";
    }
}
