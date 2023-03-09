package slogo.Node.Commands.math;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class SineCommand extends Command {

    public SineCommand() {
        this.setNumArguments(1);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            double arg = getChild(0).execute().getNumeric();
            return new NodeValue(Math.sin(Math.toRadians(arg)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
