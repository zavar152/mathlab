package itmo.zavar.mathlab.lab.lab2.command;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.lab.lab2.model.EquationSystem;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.mariuszgromada.math.mxparser.Function;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

@Command(name = "enterEquationSystem", workspace = 2)
public class EnterEquationSystemCommand extends AbstractCommand {

    protected EnterEquationSystemCommand(String name) {
        super(name);
    }

    @Override
    public void execute(AbstractWorkspace workspace, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        if(args.length != 1)
            throw new CommandException("Check input arguments");

        int n = Integer.parseInt((String) args[0]);

        Scanner scanner = new Scanner(inStream);
        PrintStream printOutStream = new PrintStream(outStream);
        Function[] functions = new Function[n];

        for (int i = 0; i < n; i++) {
            printOutStream.println("Enter your " + (i+1) + " function:");

            do {
                String enteredFunction = scanner.nextLine();
                functions[i] = new Function(getF(n) + enteredFunction);
                if(!functions[i].checkSyntax())
                    printOutStream.println(functions[i].getErrorMessage());
            } while (!functions[i].checkSyntax());
        }

        workspace.put("enteredSystem", new EquationSystem("system", functions));
        printOutStream.println("Your system: ");
        Arrays.stream(functions).forEach(function -> System.out.println(function.getFunctionExpressionString()));
    }

    private static String getF(int count) {
        StringBuilder f = new StringBuilder("f(");
        for (int i = 0; i < count; i++) {
            f.append("x").append(i + 1).append(",");
        }
        f.deleteCharAt(f.length()-1);
        f.append(")=");
        return f.toString();
    }

    @Override
    public String getHelp() {
        return "Read equation system from console";
    }
}
