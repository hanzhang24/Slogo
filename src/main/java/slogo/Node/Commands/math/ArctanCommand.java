package slogo.Node.Commands.math;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class ArctanCommand extends Command {
    public ArctanCommand() {
        this.setNumParameters(1);
    }

    public NodeValue execute() {
        checkContext();
        try {
            double arg = getChild(0).execute().getNumeric();
            return new NodeValue(Math.toDegrees(Math.atan(arg)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
