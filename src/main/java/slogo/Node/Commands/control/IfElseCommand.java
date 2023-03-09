package slogo.Node.Commands.control;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class IfElseCommand extends Command {
    public IfElseCommand() {
        this.setNumArguments(3);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        boolean conditional = Precision.asBoolean(getChild(0).execute().getNumeric());

        if (conditional) return getChild(1).execute();
        else return getChild(2).execute();
    }
}
