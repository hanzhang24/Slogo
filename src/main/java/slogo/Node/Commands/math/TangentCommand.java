package slogo.Node.Commands.math;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class TangentCommand extends Command {

    public TangentCommand() {
        this.setNumParameters(1);
    }
    public NodeValue execute() {
        checkContext();
        try {
            double arg = getChild(0).execute().getNumeric();
            return new NodeValue(Math.tan(Math.toRadians(arg)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
