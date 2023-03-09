package slogo.Node.Commands.display;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class SetShapeCommand extends Command {

    public SetShapeCommand() {
        this.setNumArguments(1);
    }

    public NodeValue execute() throws Exception {
        throw new RuntimeException("Set shape command not implemented by Model API");
    }
}
