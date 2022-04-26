package itmo.zavar.mathlab.lab.lab5.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.mariuszgromada.math.mxparser.Function;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Command(name = "euler", workspace = 5)
public class EulerCommand extends AbstractCommand {
    protected EulerCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if(args.length != 5)
            throw new CommandException("Check your arguments count");

        Scanner scanner = new Scanner(inStream);
        PrintStream printStream = new PrintStream(outStream);

        printStream.println("Enter your function:");

        Function function;

        do {
            String enteredFunction = scanner.nextLine();
            function = new Function(enteredFunction);
            if(!function.checkSyntax())
                printStream.println(function.getErrorMessage());
            else if(function.getArgumentsNumber() != 2) {
                printStream.println("Arguments number should be 2 only");
                function = new Function("");
            }
        } while (!function.checkSyntax());

        printStream.println("Enter real function:");

        Function realFunction;

        do {
            String enteredFunction = scanner.nextLine();
            realFunction = new Function(enteredFunction);
            if(!realFunction.checkSyntax())
                printStream.println(realFunction.getErrorMessage());
            else if(realFunction.getArgumentsNumber() != 1) {
                printStream.println("Arguments number should be 1 only");
                realFunction = new Function("");
            }
        } while (!realFunction.checkSyntax());

        try {
            workspace.calculate(new Object[]{printStream, args[0], args[1], args[2], args[3], function, realFunction, args[4]});
        } catch (CalculationException e) {
            throw new CommandException(e.getMessage());
        }

    }

    @Override
    public String getHelp() {
        return "Advanced Euler method. Args: x0, y0, b, dotsNumber, delta";
    }
}
