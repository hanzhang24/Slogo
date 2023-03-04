package slogo.Node.Commands.math;

import slogo.Node.Node;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class LogCommand extends Command {

    public LogCommand() {
        this.setNumParameters(1);
    }
    public NodeValue execute() {
        checkContext();
        try {
            double arg = getChild(0).execute().getNumeric();
            return new NodeValue(Math.log(arg));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
