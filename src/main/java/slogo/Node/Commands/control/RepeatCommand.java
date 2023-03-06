package slogo.Node.Commands.control;

import slogo.Node.Node;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class RepeatCommand extends Command {
    public RepeatCommand() {
        this.setNumArguments(2);
    }

    public NodeValue execute() throws Exception {
        checkContext();

        double limit = getChild(0).execute().getNumeric();

        int reps = (int) Math.round(limit);

        NodeValue result = new NodeValue(0);

        for (int i=1; i<=reps; i++) {
            model.setUserVariable(":repcount", i);
            result = getChild(1).execute();
        }

        return result;
    }
}
