package slogo.Node.Commands.display;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class ShowTurtleCommand extends Command {
    public ShowTurtleCommand() {
        this.setNumArguments(0);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            model.setAvatarVisible(true);
            return new NodeValue(1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
