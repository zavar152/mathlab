package itmo.zavar.mathlab.lab.lab3.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab2.model.SimpleFunction;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.mariuszgromada.math.mxparser.Function;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Command(name = "integral", workspace = 3)
public class IntegralCommand extends AbstractCommand {

    protected IntegralCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if (args.length != 3)
            throw new CommandException("Check input arguments");

        Scanner scanner = new Scanner(inStream);
        PrintStream printOutStream = new PrintStream(outStream);

        printOutStream.println("Enter your function:");

        Function function;

        do {
            String enteredFunction = scanner.nextLine();
            function = new Function(enteredFunction);
            if(!function.checkSyntax())
                printOutStream.println(function.getErrorMessage());
            else if(function.getArgumentsNumber() != 1) {
                printOutStream.println("Arguments number should be 1 only");
                function = new Function("");
            }
        } while (!function.checkSyntax());

        workspace.put("integral", new SimpleFunction("integral", function));
        printOutStream.println("Your integral: " + function.getFunctionExpressionString());

        try {
            workspace.calculate(new Object[]{outStream, args[0], args[1], args[2]});
        } catch (ClassCastException | CalculationException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public String getHelp() {
        return "";
    }
}
