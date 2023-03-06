package slogo.Node.Commands.control;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeCategories.Variable;
import slogo.Node.NodeValue;
import slogo.Node.NodeCategories.Group;

public class ForCommand extends Command {
    public ForCommand() {
        this.setNumParameters(2);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        try {
            Group loopConditions = (Group) getChild(0);
            Group commands = (Group) getChild(1);

            NodeValue result = new NodeValue(0);

            String varName = ((Variable)(loopConditions.getChild(0))).getName();
            double start = loopConditions.getChild(1).execute().getNumeric();
            double end = loopConditions.getChild(2).execute().getNumeric();
            double increment = loopConditions.getChild(3).execute().getNumeric();

            assert(end >= start);
            assert(increment > 0);

            for (double loop = start; loop <= end; loop += increment) {
                model.setUserVariable(varName, loop);
                result = commands.execute();
            }

            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}
