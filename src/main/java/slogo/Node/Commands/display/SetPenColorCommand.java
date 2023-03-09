package slogo.Node.Commands.display;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class SetPenColorCommand extends Command {
    public SetPenColorCommand() {
        this.setNumArguments(3);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        double r = getChild(0).execute().getNumeric();
        double g = getChild(1).execute().getNumeric();
        double b = getChild(2).execute().getNumeric();

        model.setAvatarPenColor(r, g, b);

        return new NodeValue(1);
    }
}
