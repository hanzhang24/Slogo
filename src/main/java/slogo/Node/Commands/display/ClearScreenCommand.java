package slogo.Node.Commands.display;

import slogo.Geometry.Vector;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class ClearScreenCommand extends Command {
    public ClearScreenCommand() {

        this.setNumArguments(0);
        this.setPlural(true);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            double curX = model.getAvatarX(), curY = model.getAvatarY();
            double distance = Vector.vectorFromCoord(curX, curY).getLength();
            model.resetOrientation();

            return new NodeValue(distance);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
