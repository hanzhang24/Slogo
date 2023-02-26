package Parser;

import Node.Command;

import java.util.*;

public class CommandManager {

    public static final String commandResourcePath = "resources/Parser/Commands";
    private String language = "English";
    Map<String, String> systemCommands = new HashMap<String, String>();
    public Command getSystemCommand(String alias) {
        if (!isSystemCommand(alias)) {
            throw new RuntimeException("Invalid command name " + alias);
        }
        String commandName = systemCommands.get(alias);



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

    public void loadSystemCommands() {
        ResourceBundle resources = ResourceBundle.getBundle(commandResourcePath+"/"+language+".properties");
        Enumeration<String> iter = resources.getKeys();
        while(iter.hasMoreElements()) {
            String className = iter.nextElement();
            String aliasesConcat = resources.getString(className);
            String[] aliases = aliasesConcat.trim().split("|");
            for (String alias: aliases) {
                systemCommands.put(alias.toLowerCase(), className);
            }
        }
    };

    public void setLanguage(String language) {

    };
}
