package slogo.Node.Commands.avatar;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class HideTurtleCommand extends Command {
    public HideTurtleCommand() {
        this.setNumArguments(0);
    }
    public NodeValue execute() {
        checkContext();
        try {
            model.setAvatarVisible(false);
            return new NodeValue(0);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
