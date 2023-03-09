package slogo.Node.Commands.queries;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class IdCommand extends Command {
    public IdCommand() {
        this.setNumArguments(0);
    }

    public NodeValue execute() {
        int curID = model.getCurrentAvatarID();
        return new NodeValue(curID);
    }
}
