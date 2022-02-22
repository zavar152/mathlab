package itmo.zavar.mathlab.lab.lab1;

import itmo.zavar.mathlab.annotation.Workspace;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.lab.lab1.exception.GaussException;
import itmo.zavar.mathlab.lab.lab1.model.gauss.GaussAlgorithm;
import itmo.zavar.mathlab.lab.lab1.model.gauss.GaussResult;
import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.io.OutputStream;
import java.util.HashMap;

@Workspace(name = "lab1", id = 1)
public class Lab1Workspace extends AbstractWorkspace {

    protected Lab1Workspace(HashMap<String, AbstractCommand> commandMap, String name, int id) {
        super(commandMap, name, id);
    }

    @Override
    public void calculate(Object[] args) throws GaussException {
        if(args.length != 2)
            throw new GaussException("Check input arguments");

        if(workspaceContainer.size() == 0)
            throw new GaussException("No elements in workspace");

        Matrix system;
        try {
            system = (Matrix) workspaceContainer.get("matrix");
        } catch (ClassCastException e) {
            throw new GaussException("Matrix not found");
        }

        GaussResult answer = GaussAlgorithm.calculate(system.copy(), Boolean.parseBoolean((String) args[0]));
        lastResult = answer;
        answer.print((OutputStream) args[1]);
    }
}
