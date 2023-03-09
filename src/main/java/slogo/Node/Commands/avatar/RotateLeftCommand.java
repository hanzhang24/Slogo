package slogo.Node.Commands.avatar;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class RotateLeftCommand extends Command {

    public RotateLeftCommand() {
        this.setNumArguments(1);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        try {
            double rotation = getChild(0).execute().getNumeric();
            double newRotation = model.getAvatarRotation() + rotation;

            model.setAvatarRotation(newRotation);

            return new NodeValue(newRotation);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
