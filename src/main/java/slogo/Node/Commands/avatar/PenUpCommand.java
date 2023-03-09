package slogo.Node.Commands.avatar;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class PenUpCommand extends Command {
    public PenUpCommand() {
        this.setNumArguments(0);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            model.setAvatarPenDown(false);

            return new NodeValue(0);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
