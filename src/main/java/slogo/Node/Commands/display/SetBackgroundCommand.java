package slogo.Node.Commands.display;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class SetBackgroundCommand extends Command {

    public SetBackgroundCommand() {
        this.setNumArguments(1);
    }

    public NodeValue execute() throws Exception {
        throw new RuntimeException("Set background not implemented by Model API");
    }
}
