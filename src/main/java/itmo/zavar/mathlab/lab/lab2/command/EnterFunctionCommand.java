package itmo.zavar.mathlab.lab.lab2.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab2.model.FunctionWithDerivative;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.mariuszgromada.math.mxparser.Function;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Command(name = "enterFunction", workspace = 2)
public class EnterFunctionCommand extends AbstractCommand {

    protected EnterFunctionCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
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

        printOutStream.println("Enter function's derivative:");

        Function derivative;

        do {
            String enteredFunction = scanner.nextLine();
            derivative = new Function(enteredFunction);
            if(!derivative.checkSyntax())
                printOutStream.println(derivative.getErrorMessage());
            else if(derivative.getArgumentsNumber() != 1) {
                printOutStream.println("Arguments number should be 1 only");
                derivative = new Function("");
            }
        } while (!derivative.checkSyntax());

        workspace.put("enteredFunction", new FunctionWithDerivative("function", function, derivative));
        printOutStream.println("Your function: " + function.getFunctionExpressionString());
        printOutStream.println("Function's derivative: " + derivative.getFunctionExpressionString());
    }

    @Override
    public String getHelp() {
        return "Read function from console";
    }
}
