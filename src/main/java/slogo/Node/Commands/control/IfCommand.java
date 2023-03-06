package slogo.Node.Commands.control;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class IfCommand extends Command {
    public IfCommand() {
        this.setNumParameters(2);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        boolean conditional = Precision.asBoolean(getChild(0).execute().getNumeric());

        if (conditional) {
            return getChild(1).execute();
        }
        return new NodeValue(0);
    }
}
