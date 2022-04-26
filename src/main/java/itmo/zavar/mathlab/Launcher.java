package itmo.zavar.mathlab;

import itmo.zavar.mathlab.interpreter.ConsoleInterpreter;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import itmo.zavar.mathlab.workspace.WorkspaceInitializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

//new Function("f(x, y)=x^2-2*y")
//new Function("f(x)=0.75*exp(-2*x)+0.5*x^2-0.5*x+0.25")
public class Launcher {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        System.out.println("Loading...");
        Integer id = WorkspaceInitializer.getWorkspaceId();
        AbstractWorkspace workspace = WorkspaceInitializer.createWorkspace(id);
        ConsoleInterpreter interpreter = new ConsoleInterpreter(workspace, System.in, System.out, System.err);
        interpreter.launch();
    }
}
