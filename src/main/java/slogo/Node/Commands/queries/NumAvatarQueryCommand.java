package slogo.Node.Commands.queries;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class NumAvatarQueryCommand extends Command {
    public NumAvatarQueryCommand() {
        this.setNumArguments(0);
    }

    public NodeValue execute() {
        int numAvatars = model.getTotalNumberOfAvatars();
        return new NodeValue(numAvatars);
    }
}
