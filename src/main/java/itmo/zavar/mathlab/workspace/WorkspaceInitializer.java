package itmo.zavar.mathlab.workspace;

import itmo.zavar.mathlab.annotation.Command;
import itmo.zavar.mathlab.annotation.CommandGeneral;
import itmo.zavar.mathlab.annotation.Workspace;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.reflections.Reflections;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class WorkspaceInitializer {

    private static final Reflections reflections = new Reflections();
    private static final HashMap<String, AbstractCommand> commandMap = new HashMap<>();
    private static final HashMap<Integer, Class<?>> workspaceClasses = new HashMap<>();
    private static final HashMap<Integer, String> workspaceNames = new HashMap<>();

    static {
        reflections.getTypesAnnotatedWith(Workspace.class).forEach(aClass -> {
            Workspace annotation = aClass.getDeclaredAnnotationsByType(Workspace.class)[0];
            workspaceClasses.put(annotation.id(), aClass);
            workspaceNames.put(annotation.id(), annotation.name());
        });
    }

    public static Integer getWorkspaceId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Found workspaces:");
        workspaceNames.forEach((integer, s) -> System.out.println(integer + ": " + s));

        int id = 0;
        while (true) {
            try {
                id = scanner.nextInt();

                if (!workspaceClasses.containsKey(id))
                    System.err.println("Enter a number from list");
                else
                    break;
            } catch (InputMismatchException e) {
                System.err.println("Enter a number");
                scanner.next();
            } catch (NoSuchElementException e) {
                System.err.println("Input stream is closed!");
                System.exit(0);
            }
        }

        return id;
    }

    public static AbstractWorkspace createWorkspace(Integer id) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        if (!workspaceClasses.containsKey(id))
            throw new NullPointerException("Wrong workspace id");

        reflections.getTypesAnnotatedWith(CommandGeneral.class).forEach(aClass -> {
            try {
                Constructor<?> constructor = aClass.getDeclaredConstructor(String.class);
                constructor.setAccessible(true);
                CommandGeneral annotation = aClass.getDeclaredAnnotationsByType(CommandGeneral.class)[0];
                AbstractCommand com = (AbstractCommand) constructor.newInstance(annotation.name());
                commandMap.put(com.getName(), com);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

        reflections.getTypesAnnotatedWith(Command.class).forEach(aClass -> {
            try {
                Constructor<?> constructor = aClass.getDeclaredConstructor(String.class);
                constructor.setAccessible(true);
                Command annotation = aClass.getDeclaredAnnotationsByType(Command.class)[0];
                if (annotation.workspace() == id) {
                    AbstractCommand com = (AbstractCommand) constructor.newInstance(annotation.name());
                    commandMap.put(com.getName(), com);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

        Constructor<?> constructor = workspaceClasses.get(id).getDeclaredConstructor(HashMap.class, String.class, int.class);
        constructor.setAccessible(true);
        return (AbstractWorkspace) constructor.newInstance(commandMap, workspaceNames.get(id), id);
    }
}
