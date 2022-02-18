package itmo.zavar.mathlab.lab.lab1;

import itmo.zavar.mathlab.annotation.Workspace;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.util.HashMap;

@Workspace(name = "lab1", id = 1)
public class Lab1Workspace extends AbstractWorkspace {

    protected Lab1Workspace(HashMap<String, AbstractCommand> commandMap, String name, int id) {
        super(commandMap, name, id);
    }

    @Override
    public void calculate() {

    }
}
