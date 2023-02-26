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
        } catch (Exception e) {
            // TODO: Handle exceptions
        }
    }
    private Node parseExpression(){
        try {

        } catch (Exception e) {
            // TODO: Handle exceptions
        }
    };



    private Node parseGroup(){};

    private Node parseList(){};

    private Node parseCommand(){};

    private Node parseConstant(){};

    private Node parseVariable(){};


}
