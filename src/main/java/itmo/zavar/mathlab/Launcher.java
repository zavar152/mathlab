package itmo.zavar.mathlab;

import itmo.zavar.mathlab.interpreter.ConsoleInterpreter;
import itmo.zavar.mathlab.lab.lab1.model.Matrix;
import itmo.zavar.mathlab.lab.lab1.model.MatrixUtils;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import itmo.zavar.mathlab.workspace.WorkspaceInitializer;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Launcher {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        double[][] test = {{1,2,3},{4,5,6},{7,8,9}};
        Matrix matrix = new Matrix("test", test);

        Arrays.stream(matrix.getColumn(2)).forEach(System.out::println);

        System.out.println(matrix);

        Integer id = WorkspaceInitializer.getWorkspaceId();
        AbstractWorkspace workspace = WorkspaceInitializer.createWorkspace(id);
        ConsoleInterpreter interpreter = new ConsoleInterpreter(workspace, System.in, System.out, System.err);
        interpreter.launch();
    }
}
