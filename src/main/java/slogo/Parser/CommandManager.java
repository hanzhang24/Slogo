package slogo.Parser;

import slogo.Node.Command;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CommandManager {

    private static final String commandResourcePath = "resources/Parser/Commands";
    private static final String basePackage = "slogo.Node.Commands";
    private String language = "English";
    Map<String, Class<?>> systemCommands = new HashMap<String, Class<?>>();
    public Command getSystemCommand(String alias) {
        if (!isSystemCommand(alias.toLowerCase())) {
            throw new RuntimeException("Invalid command name " + alias);
        }
        try {
            Class<?> commandClass = systemCommands.get(alias.toLowerCase());
            return (Command) commandClass.getConstructor().newInstance();
        } catch (Exception e) {
            // TODO: Handle this exception
            throw new RuntimeException(e.getMessage());
        }
    };

    public Command getUserCommand(String alias) {
        throw new RuntimeException("Not implemented");
    }
    public boolean isSystemCommand(String name) {
        return systemCommands.containsKey(name.toLowerCase());
    }
    public boolean isCustomCommand(String name) {
        throw new RuntimeException("Not implemented");
    }

    public void createUserCommand() {
        throw new RuntimeException("Not implemented");
    }
    public void loadSystemCommands() throws ClassNotFoundException {
        ResourceBundle resources = ResourceBundle.getBundle(commandResourcePath+"/"+language+".properties");
        Enumeration<String> iter = resources.getKeys();
        while(iter.hasMoreElements()) {
            String classPackageAndName = iter.nextElement();
            String aliasesConcat = resources.getString(classPackageAndName);
            String[] aliases = aliasesConcat.trim().split("|");
            for (String alias: aliases) {
                systemCommands.put(alias.toLowerCase(), Class.forName(basePackage+"."+classPackageAndName));
            }
        }
    };

    public void setLanguage(String language) {
        this.language = language;
    };
}
