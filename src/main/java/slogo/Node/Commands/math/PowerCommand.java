package slogo.Node.Commands.math;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class PowerCommand extends Command {

    public PowerCommand() {
        this.setNumParameters(2);
    }
    public NodeValue execute() {
        checkContext();
        try {
            double base = getChild(0).execute().getNumeric();
            double exponent = getChild(1).execute().getNumeric();
            return new NodeValue(Math.pow(base, exponent));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
