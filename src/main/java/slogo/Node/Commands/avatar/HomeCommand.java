package slogo.Node.Commands.avatar;

import slogo.Geometry.Vector;
import slogo.Node.Command;
import slogo.Node.Node;
import slogo.Node.NodeValue;

public class HomeCommand extends Command {
    public int getNumParameters() {
        return 0;
    }

    public NodeValue execute() {
        checkContext();
        try {
            double x = model.getAvatarX(), y = model.getAvatarY(), rotation = model.getAvatarRotation();

            Vector v = Vector.vectorFromCoord(x, y);

            model.setAvatarPosition(0, 0);

            return new NodeValue(v.getLength());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

