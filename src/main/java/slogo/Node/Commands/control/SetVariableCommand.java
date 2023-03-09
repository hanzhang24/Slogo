package slogo.Node.Commands.control;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeCategories.Variable;
import slogo.Node.NodeValue;

public class SetVariableCommand extends Command {
    public SetVariableCommand() {
        this.setNumArguments(2);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        String varName = ((Variable)(getChild(0))).getName();
        double value = getChild(1).execute().getNumeric();

        model.setUserVariable(varName, value);

        return new NodeValue(value);
    }

}
