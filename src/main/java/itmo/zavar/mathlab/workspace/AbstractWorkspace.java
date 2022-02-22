package itmo.zavar.mathlab.workspace;

import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.exception.CommandException;
import itmo.zavar.mathlab.workspace.common.MathObject;
import itmo.zavar.mathlab.workspace.common.Result;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorkspace {

    protected final Map<String, AbstractCommand> commandMap;
    protected final HashMap<String, MathObject> workspaceContainer = new HashMap<>();
    protected final String name;
    protected final int id;
    protected Result lastResult;
    protected boolean requiredShutdown = false;

    protected AbstractWorkspace(HashMap<String, AbstractCommand> commandMap, String name, int id) {
        this.commandMap = Collections.unmodifiableMap(commandMap);
        this.name = name;
        this.id = id;
    }

    public abstract void calculate(Object[] args);

    public final boolean isRequiredShutdown() {
        return requiredShutdown;
    }

    public final void requiredShutdown() {
        requiredShutdown = true;
    }

    public final boolean isCommandExists(String name) {
        return commandMap.containsKey(name);
    }

    public final void executeCommand(String name, Object[] args, InputStream inStream, OutputStream outStream, OutputStream errStream) throws CommandException {
        commandMap.get(name).execute(this, args, inStream, outStream, errStream);
    }

    public final Result getLastResult() {
        return lastResult;
    }

    public final void put(String key, MathObject object) {
        workspaceContainer.put(key, object);
    }

    public final void remove(String key) {
        workspaceContainer.remove(key);
    }

    public final MathObject get(String key) {
        return workspaceContainer.get(key);
    }

    public final Map<String, MathObject> getWorkspaceContainer() {
        return Collections.unmodifiableMap(workspaceContainer);
    }

    public final Map<String, AbstractCommand> getCommandMap() {
        return commandMap;
    }

    public final String getName() {
        return name;
    }

    public final int getId() {
        return id;
    }
}
