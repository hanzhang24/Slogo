package slogo.Controller;

import slogo.Node.NodeValue;
import slogo.Node.NodeCategories.Root;
import slogo.Parser.CommandManager;
import slogo.Payload.ViewPayloadManager.ViewPayload;
import slogo.Model.*;

public class Controller {

    private ViewPayload viewPayload;
    private Model model;
    private ViewController viewController;

    private CommandManager commandManager;
    public Controller() {
        this.model = new ModelTracker();
        this.commandManager = new CommandManager();
    }
    NodeValue runInput(String string) {

    };

    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }

    void runViewPayload(ViewPayload viewPayload) {
        viewController.runPayload()
    }




}
