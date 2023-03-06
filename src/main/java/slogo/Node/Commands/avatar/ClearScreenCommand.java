package slogo.Node.Commands.avatar;

import slogo.Geometry.Vector;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class ClearScreenCommand extends Command {
    public ClearScreenCommand() {
        this.setNumArguments(0);
    }
    public NodeValue execute() {
        checkContext();
        try {
            double curX = model.getAvatarX(), curY = model.getAvatarY();
            double distance = Vector.vectorFromCoord(curX, curY).getLength();

            System.out.println("No clearscreen support yet");
            model.setAvatarPosition(0, 0);
            // model.clearScreen();

            return new NodeValue(distance);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
