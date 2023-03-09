package slogo.Node.Commands.queries;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class ShapeQueryCommand extends Command {
    public ShapeQueryCommand() {
        this.setNumArguments(0);
    }

    public NodeValue execute() throws Exception {
        throw new Exception("Shape query not implemented by Model API");
    }

}
