package slogo.Node.Commands.avatar;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class HideTurtleCommand extends Command {
    public HideTurtleCommand() {
        this.setNumParameters(0);
    }
    public NodeValue execute() {
        checkContext();
        try {
            // TODO: CURRENTLY DOES NOTHING
            // model.setAvatarShow(false)
            System.out.println("Model has not interface to show/hide turtle yet");
            return new NodeValue(0);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
