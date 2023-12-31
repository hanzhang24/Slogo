package slogo.Node.Commands.math;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class DifferenceCommand extends Command {

    public DifferenceCommand() {
        this.setNumArguments(2);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        try {
            double arg_1 = getChild(0).execute().getNumeric();
            double arg_2 = getChild(1).execute().getNumeric();

            return new NodeValue(arg_1 - arg_2);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
