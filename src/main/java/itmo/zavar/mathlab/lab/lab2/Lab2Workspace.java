package itmo.zavar.mathlab.lab.lab2;

import itmo.zavar.mathlab.annotation.Workspace;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;

import java.util.HashMap;

@Workspace(name = "lab2", id = 2)
public class Lab2Workspace extends AbstractWorkspace {

    protected Lab2Workspace(HashMap<String, AbstractCommand> commandMap, String name, int id) {
        super(commandMap, name, id);
    }

    @Override
    public void calculate() {

    }
}
