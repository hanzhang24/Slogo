package slogo.Parser;
import slogo.Node.*;
import slogo.Node.NodeCategories.*;
public class Parser {
    private Tokenizer tokenizer;
    private CommandManager commandManager;
    public Parser(){};
    public Parser(CommandManager commandManager) {this.commandManager = commandManager;}
    public Node parseInput(String input) throws Exception {
        this.tokenizer = new Tokenizer(input);
        if (tokenizer.isEndOfInput()) return null;
        Root root = parseAll();
        return root;
    }
    private Root parseAll() throws Exception {
        Root root = new Root();
        while(!tokenizer.isEndOfInput()) {
            Node node = parseExpression();
            root.addChild(node);
        }
        return root;
    }
    private Node parseExpression() throws Exception {
        String curToken = tokenizer.getCurToken();
        TokenType type = TypeChecker.getType(curToken);
        return type.parse(this);
    }
    public Node parseGroup() throws Exception {
        Group group = new Group();
        String curToken = tokenizer.getCurToken();
        assert(TypeChecker.getType(curToken) == TokenType.GROUP_START);
        tokenizer.toNextToken();

        while(!tokenizer.isEndOfInput() &&
              TypeChecker.getType(tokenizer.getCurToken()) != TokenType.GROUP_END) {
            Node child = parseExpression();
            group.addChild(child);
        }

        assert(!tokenizer.isEndOfInput());
        assert(TypeChecker.getType(tokenizer.getCurToken()) == TokenType.GROUP_END);
        tokenizer.toNextToken();

        return group;
    };

    public Node parseCommand() throws Exception {
        String curToken = tokenizer.getCurToken();
        if (commandManager.isSimpleDefinedCommand(curToken)) {
            return parseSimpleDefinedCommand();
        } else if (commandManager.isCreateFunctionCommand(curToken)) {
            return parseCreateCommand();
        } else {
            throw new NoSuchMethodException("No such command" + tokenizer.getCurToken());
        }
    };
    private Node parseSimpleDefinedCommand() throws Exception {
        Command command = commandManager.getSimpleDefinedCommand(tokenizer.getCurToken());
        int numParameters = command.getNumArguments();
        tokenizer.toNextToken();
        for (int i = 0; i < numParameters; i++) {
            Node child = parseExpression();
            command.addChild(child);
        }
        return command;
    }

    public Node parseRepeatedCommand() throws Exception {

        String curToken = tokenizer.getCurToken();
        assert(TypeChecker.getType(curToken) == TokenType.REPEATER_GROUP_BEGIN);
        tokenizer.toNextToken();

        String commandName = tokenizer.getCurToken();
        if (!commandManager.isSimpleDefinedCommand(commandName)) {
            if (commandManager.isCreateFunctionCommand(commandName)) {
                throw new Exception("Repeated function creation not supported");
            } else
                throw new Exception("Command " + commandName + " not found");
        }
        Node command = commandManager.getSimpleDefinedCommand(commandName);
        tokenizer.toNextToken();

        while(!tokenizer.isEndOfInput() && TypeChecker.getType(tokenizer.getCurToken()) != TokenType.REPEATER_GROUP_END) {
            Node child = parseExpression();
            command.addChild(child);
        }

        if (tokenizer.isEndOfInput()) {
            throw new Exception("Reached end of input before )");
        } else if (!command.hasCompatibleNumChildren()) {
            throw new Exception(command.getChildren().size() + " is not a compatible number of arguments for repeating " + commandName);
        }
        assert(TypeChecker.getType(tokenizer.getCurToken()) != TokenType.REPEATER_GROUP_END);
        tokenizer.toNextToken();

        return command;
    }
    private Node parseCreateCommand() throws Exception {

        String curToken = tokenizer.getCurToken();
        assert(commandManager.isCreateFunctionCommand(curToken));
        tokenizer.toNextToken();

        // parse the name of the function, and make sure it makes sense
        String functionName = tokenizer.getCurToken();
        if (TypeChecker.getType(functionName) != TokenType.POSSIBLY_COMMAND) {
            throw new Exception("Invalid create command format for name " + functionName);
        } else if (commandManager.isSystemCommand(functionName) || commandManager.isCreateFunctionCommand(functionName)) {
            throw new Exception("Cannot define command " + functionName + " because this command name is reserved for system commands");
        }
        tokenizer.toNextToken();

        // parse the variables of the function
        Group arguments = (Group) parseGroup();

        // parse the body of the function
        Group body = (Group) parseGroup();

        // now create the function template
        UserFunctionTemplate uft = new UserFunctionTemplate(functionName, arguments, body);

        commandManager.createUserCommand(uft);

        return new Constant(1);
    }

    public Node parseConstant(){
        Node constant = new Constant(Double.parseDouble(tokenizer.getCurToken()));
        tokenizer.toNextToken();
        return constant;
    };

    public Node parseVariable(){
        Node variable = new Variable(tokenizer.getCurToken());
        tokenizer.toNextToken();
        return variable;
    };


}
