package slogo.Parser;
import slogo.Node.*;
import slogo.Controller.*;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeCategories.Constant;
import slogo.Node.NodeCategories.Root;
import slogo.Node.NodeCategories.Variable;

public class Parser {
    private Tokenizer tokenizer;
    private CommandManager commandManager;
    public Parser(){};
    public Parser(CommandManager commandManager) {this.commandManager = commandManager;}
    public Node parseInput(String input) {
        try {
            this.tokenizer = new Tokenizer(input);
            Root root = parseAll();
            return root;
        } catch (Exception e) {
            // TODO: Handle exceptions
            throw new RuntimeException(e.getMessage());
        }
    }
    private Root parseAll() {
        try {
            Root root = new Root();
            while(!tokenizer.isEndOfInput()) {
                Node node = parseExpression();
                root.addChild(node);
            }
            return root;
        } catch (Exception e) {
            // TODO: Handle exceptions
            throw new RuntimeException("parseAll has not implemented error handling");
        }
    }
    private Node parseExpression(){
        try {
            String curToken = tokenizer.getCurToken();
            TokenType type = TypeChecker.getType(curToken);
            return type.parse(this);
        } catch (Exception e) {
            // TODO: Handle exceptions
            throw new RuntimeException("Not implemented");
        }
    };

    public Node parseGroup(){
        throw new RuntimeException("Not implemented");
    };

    public Node parseCommand() throws NoSuchMethodException {
        if (commandManager.isSystemCommand(tokenizer.getCurToken())) {
            return parseSystemCommand();
        } else if (commandManager.isCustomCommand(tokenizer.getCurToken())) {
            throw new RuntimeException("Not implemented");
        } else {
            //TODO make this language specific
            throw new NoSuchMethodException("No such command" + tokenizer.getCurToken());
        }
    };
    private Node parseSystemCommand(){
        Command command = commandManager.getSystemCommand(tokenizer.getCurToken());
        int numParameters = command.getNumParameters();
        tokenizer.toNextToken();
        for (int i = 0; i < numParameters; i++) {
            Node child = parseExpression();
            command.addChild(child);
        }
        return command;
    }

    public Node parseCustomCommand(){
        throw new RuntimeException("Not implemented");
    }

    public Node parseConstant(){
        Node constant = new Constant(tokenizer.getCurToken());
        tokenizer.toNextToken();
        return constant;
    };

    public Node parseVariable(){
        Node variable = new Variable(tokenizer.getCurToken());
        tokenizer.toNextToken();
        return variable;
    };


}
