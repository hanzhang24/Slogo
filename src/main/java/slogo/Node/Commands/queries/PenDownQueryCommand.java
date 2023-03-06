package slogo.Node.Commands.queries;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class PenDownQueryCommand extends Command {
    public PenDownQueryCommand() {
        this.setNumParameters(0);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        boolean penDown = model.getAvatarIsPenDown();
        double result = Precision.asDouble(penDown);
        return new NodeValue(result);
    }

}
