package itmo.zavar.mathlab.lab.lab4;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.mariuszgromada.math.mxparser.Function;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Command(name = "spline", workspace = 4)
public class SplineCommand extends AbstractCommand {
    protected SplineCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if(args.length != 2)
            throw new CommandException("Enter a count");

        int number;
        try {
            number = Integer.parseInt((String) args[0]);
        } catch (NumberFormatException e) {
            throw new CommandException("Enter a number");
        }

        double delta;
        try {
            delta = Double.parseDouble((String) args[1]);
        } catch (NumberFormatException e) {
            throw new CommandException("Enter a delta");
        }

        Scanner scanner = new Scanner(inStream);
        PrintStream printStream = new PrintStream(outStream);
        double[][] elements = new double[2][number];

        for(int i = 1; i <= 2; i++) {
            try {
                printStream.print(i == 1 ? "x:" : "y:");
                String r = scanner.nextLine();
                double[] temp = Arrays.stream(r.split(" ")).mapToDouble(Double::parseDouble).toArray();
                if(temp.length != number) {
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

        printStream.println("Enter your function:");

        Function function;

        do {
            String enteredFunction = scanner.nextLine();
            function = new Function(enteredFunction);
            if(!function.checkSyntax())
                printStream.println(function.getErrorMessage());
            else if(function.getArgumentsNumber() != 1) {
                printStream.println("Arguments number should be 1 only");
                function = new Function("");
            }
        } while (!function.checkSyntax());

        try {
            workspace.calculate(new Object[]{elements[0], elements[1], function, delta});
        } catch (CalculationException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public String getHelp() {
        return "Use spline method to interpolate function from table";
    }
}
