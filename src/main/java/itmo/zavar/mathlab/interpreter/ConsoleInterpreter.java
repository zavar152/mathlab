package itmo.zavar.mathlab.interpreter;

import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class ConsoleInterpreter {

    private final AbstractWorkspace workspace;
    private final InputStream inStream;
    private final OutputStream outStream;
    private final OutputStream errStream;

    public ConsoleInterpreter(AbstractWorkspace workspace, InputStream inStream, OutputStream outStream, OutputStream errStream) {
        this.workspace = workspace;
        this.inStream = inStream;
        this.outStream = outStream;
        this.errStream = errStream;
    }

    public void launch() {
        new Thread(() -> {
            Scanner scanner = new Scanner(inStream);
            PrintStream outPrinter = new PrintStream(outStream);
            PrintStream errPrinter = new PrintStream(errStream);
            outPrinter.println("Interpreter started, workspace name: " + workspace.getName() + ". Enter 'help' to get available commands");
            while(!workspace.isRequiredShutdown()) {
                try {
                    String[] command = scanner.nextLine().replaceAll(" +", " ").trim().split(" ");

                    if (workspace.isCommandExists(command[0])) {
                        workspace.executeCommand(command[0], Arrays.copyOfRange(command, 1, command.length), inStream, outStream, errStream);
                    } else {
                        errPrinter.println("Unknown command! Use 'help'");
                    }
                } catch (CommandException e) {
                  errPrinter.println(e.getMessage());
                } catch (NoSuchElementException e) {
                    errPrinter.println("Input stream is closed!");
                    workspace.requiredShutdown();
                }
            }
        }).start();
    }
}
