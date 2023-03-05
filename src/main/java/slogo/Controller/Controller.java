package slogo.Controller;

import slogo.Node.Node;
import slogo.Node.NodeValue;
import slogo.Node.NodeCategories.Root;
import slogo.Parser.CommandManager;
import slogo.Payload.ViewPayloadManager.ViewPayload;
import slogo.Model.*;
import slogo.Parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Model model;
    private ViewController viewController;
    private CommandManager commandManager;
    public Controller() throws ClassNotFoundException {
        this.model = new ModelTracker();
        this.commandManager = new CommandManager();
    }
    public NodeValue runInput(String input) {
        try {
            Parser parser = new Parser(commandManager);
            Root root = (Root) parser.parseInput(input);
            root.initContext(model);
            model.startOp();
            NodeValue result = root.execute();
            List<Double> returnValues = root.getReturnValues();
            ViewPayload viewPayload = model.endOp(input, returnValues);
            viewController.runPayload(viewPayload);
            return result;
        } catch (Exception e) {
            model.bail();
            throw new RuntimeException("Not implemented");
        }
    };

    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }
}
