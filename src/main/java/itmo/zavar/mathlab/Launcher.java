package itmo.zavar.mathlab;

import itmo.zavar.mathlab.interpreter.ConsoleInterpreter;
import itmo.zavar.mathlab.lab.lab1.model.gauss.GaussAlgorithm;
import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.lab.lab1.model.matrix.MatrixCreator;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import itmo.zavar.mathlab.workspace.WorkspaceInitializer;

import java.lang.reflect.InvocationTargetException;

public class Launcher {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Matrix matrix = MatrixCreator.fromString("Matrix", "-3 2 -4 3\n0 3 3 15\n-1 -3 1 14");

        System.out.println(matrix);

        GaussAlgorithm.calculate(matrix);

//        Integer id = WorkspaceInitializer.getWorkspaceId();
//        AbstractWorkspace workspace = WorkspaceInitializer.createWorkspace(id);
//        ConsoleInterpreter interpreter = new ConsoleInterpreter(workspace, System.in, System.out, System.err);
//        interpreter.launch();
    }
}
