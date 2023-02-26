package Parser;
import Node.*;
import Controller.*;

public class Parser {
    private Tokenizer tokenizer;
    private Controller controller;
    public void setController(Controller controller) {
        this.controller = controller;
    }
    public NodeValue parseInput(String input) {
        try {
            this.tokenizer = new Tokenizer(input);
            Root root = parseAll();
            NodeValue result = controller.runCommand(root);
            return result;
        } catch (Exception e) {
            // TODO: Handle exceptions
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
        }
    }
    private Node parseExpression(){
        try {
            String curToken = tokenizer.getCurToken();
            switch (TypeChecker.getType(curToken)) {
                case CONSTANT:
                    return parseConstant();
                case VARIABLE:
                    return parseVariable();
                case GROUP_START:
                    throw new RuntimeException("Not yet implemented");
                    break;
                case POSSIBLY_COMMAND:


                    break;
                case BAD_TYPE, GROUP_END:
                    // TODO: Paramerize the error message
                    throw new IllegalArgumentException("Invalid token " + curToken);
            }
        } catch (Exception e) {
            // TODO: Handle exceptions
        }
    };



    private Node parseGroup(){
        throw new RuntimeException();
    };

    private Node parseCommand(){

    };

    private parseSystemCommand(){
    }

    private parseCustomCommand(){

    }

    private Node parseConstant(){
        Node constant = new Constant(tokenizer.getCurToken());
        tokenizer.toNextToken();
        return constant;
    };

    private Node parseVariable(){
        Node variable = new Variable(curToken);
        tokenizer.toNextToken();
        return variable;
    };


}
