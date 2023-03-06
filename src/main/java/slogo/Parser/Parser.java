package slogo.Parser;
import slogo.Node.*;
import slogo.Node.NodeCategories.*;

public class Parser {
    private Tokenizer tokenizer;
    private CommandManager commandManager;
    public Parser(){};
    public Parser(CommandManager commandManager) {this.commandManager = commandManager;}
    public Node parseInput(String input) {
        try {
            this.tokenizer = new Tokenizer(input);
            if (tokenizer.isEndOfInput()) return null;
            Root root = parseAll();
            return root;
        } catch (Exception e) {
            // TODO: Handle exceptions
            throw new RuntimeException(e.getMessage());
        }
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
    };

    public Node parseGroup() throws Exception {
        try {
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
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

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

    private Node parseCreateCommand() throws Exception {

        String curToken = tokenizer.getCurToken();
        assert(commandManager.isCreateFunctionCommand(curToken));

        // parse the name of the function, and make sure it makes sense
        tokenizer.toNextToken();
        String functionName = tokenizer.getCurToken();

        if (TypeChecker.getType(functionName) != TokenType.POSSIBLY_COMMAND)
            throw new Exception("Invalid create command format for name " + functionName);

        if (commandManager.isSystemCommand(functionName) || commandManager.isCreateFunctionCommand(functionName)) {
            throw new Exception("Cannot define command " + functionName + " because this command name is reserved for system commands");
        }

        // parse the variables of the function
        tokenizer.toNextToken();
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
