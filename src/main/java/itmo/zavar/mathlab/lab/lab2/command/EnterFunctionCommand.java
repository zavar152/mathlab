package itmo.zavar.mathlab.lab.lab2.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab2.model.SimpleFunction;
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

        workspace.put("enteredFunction", new SimpleFunction("function", function));
        printOutStream.println("Your function: " + function.getFunctionExpressionString());
    }

    @Override
    public String getHelp() {
        return "Read function from console";
    }
}
