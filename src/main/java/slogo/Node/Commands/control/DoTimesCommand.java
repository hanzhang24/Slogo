package slogo.Node.Commands.control;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeCategories.Variable;
import slogo.Node.NodeValue;

public class DoTimesCommand extends Command {
    public DoTimesCommand() {
        this.setNumArguments(2);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            NodeValue result = new NodeValue(0);

            String varName = ((Variable) (getChild(0).getChild(0))).getName();
            double limit = getChild(0).getChild(1).execute().getNumeric();

            for (int loop = 1; loop <= limit; loop++) {
                model.setUserVariable(varName, loop);
                result = getChild(1).execute();
            }
            return result;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
