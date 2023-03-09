package slogo.Node.Commands.avatar;

import slogo.Geometry.Vector;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class GotoCommand extends Command {
    public GotoCommand() {
        this.setNumArguments(2);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            double x = getChild(0).execute().getNumeric(), y = getChild(1).execute().getNumeric();
            Vector newPos = Vector.vectorFromCoord(x, y);
            Vector oldPos = Vector.vectorFromCoord(model.getAvatarX(), model.getAvatarY());
            double distance = newPos.subtract(oldPos).getLength();
            model.setAvatarPosition(x, y);

            return new NodeValue(distance);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
