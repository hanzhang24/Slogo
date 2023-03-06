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
    public NodeValue runInput(String input) throws Exception {
        try {
            Parser parser = new Parser(commandManager);
            Node uncasted = parser.parseInput(input);
            if (uncasted == null)
                throw new Exception("input had no runnable commands");
            Root root = (Root) uncasted;
            root.initContext(model);
            model.startOp();
            NodeValue result = root.execute();
            List<Double> returnValues = root.getReturnValues();
            ViewPayload viewPayload = model.endOp(input, returnValues);
            viewController.runPayload(viewPayload);
            return result;
        } catch (Exception e) {
            model.bail();
            throw e;
        }
    };

    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }
}
