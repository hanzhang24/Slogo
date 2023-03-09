package slogo.Node.Commands.avatar;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class PenDownCommand extends Command {
    public PenDownCommand() {
        this.setNumArguments(0);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            model.setAvatarPenDown(true);

            return new NodeValue(1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
