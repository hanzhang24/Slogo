package slogo.Parser;

import slogo.Node.NodeCategories.Command;
import slogo.Node.UserFunctionTemplate;

import java.util.*;

public class CommandManager {
    private static final String commandResourcePath = "Parser.Commands";
    private static final String basePackage = "slogo.Node.Commands";

    private static final String createCommandKey = "CREATE_COMMAND";
    private static String language = "English";

    Map<String, Class<?>> systemCommands = new HashMap<String, Class<?>>();
    Map<String, UserFunctionTemplate> userCommands = new HashMap<String, UserFunctionTemplate>();
    Set<String> createFunctionCommandAliases = new HashSet<String>();

    public CommandManager() throws ClassNotFoundException {
        this.loadSystemCommands();
    }
    public Command getSimpleDefinedCommand(String alias) throws Exception {
        if (isSystemCommand(alias)) return getSystemCommand(alias);
        else if (isUserCommand(alias)) return getUserCommand(alias);
        else throw new Exception("Invalid command name " + alias);
    }
    public boolean isSimpleDefinedCommand(String alias) throws Exception {
        return isSystemCommand(alias) || isUserCommand(alias);
    }
    public Command getSystemCommand(String alias) throws Exception {
        if (!isSystemCommand(alias.toLowerCase())) {
            throw new RuntimeException("Invalid command name " + alias);
        }
        Class<?> commandClass = systemCommands.get(alias.toLowerCase());
        return (Command) commandClass.getConstructor().newInstance();
    };
    public void createUserCommand(UserFunctionTemplate userFunctionTemplate) {
        userCommands.put(userFunctionTemplate.getName().toLowerCase(), userFunctionTemplate);
    }
    public Command getUserCommand(String alias) throws Exception {
        if (!isUserCommand(alias.toLowerCase())) {
            throw new RuntimeException("Invalid command name " + alias);
        }
        UserFunctionTemplate uft = userCommands.get(alias.toLowerCase());
        Command command = uft.makeInstance();
        return command;
    }
    public boolean isSystemCommand(String name) {
        return systemCommands.containsKey(name.toLowerCase());
    }
    public boolean isUserCommand(String name) {
        return userCommands.containsKey(name.toLowerCase());
    }
    public boolean isCreateFunctionCommand(String name) {
        return createFunctionCommandAliases.contains(name.toLowerCase());
    }
    public void loadSystemCommands() throws ClassNotFoundException {
        ResourceBundle resources = ResourceBundle.getBundle(commandResourcePath+"."+language);
        Enumeration<String> iter = resources.getKeys();
        boolean firstRow = true;
        while(iter.hasMoreElements()) {
            String key = iter.nextElement();
            String aliasesConcat = resources.getString(key);
            String[] aliases = aliasesConcat.trim().split("[|]");
            if (key.equals(createCommandKey)) {
                createFunctionCommandAliases.addAll(Arrays.asList(aliases));
            } else for (String alias: aliases) {
                systemCommands.put(alias.toLowerCase(), Class.forName(basePackage+"."+key+"Command"));
            }
        }
    };

    public static void setLanguage(String language) {
        CommandManager.language = language;
    };
}
