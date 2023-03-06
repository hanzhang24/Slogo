package slogo.Node.Commands.avatar;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class ShowTurtleCommand extends Command {
    public ShowTurtleCommand() {
        this.setNumArguments(0);
    }
    public NodeValue execute() {
        checkContext();
        try {
            // TODO: CURRENTLY DOES NOTHING
            // model.setAvatarShow(true)
            System.out.println("Model has not interface to show/hide turtle yet");
            return new NodeValue(1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
