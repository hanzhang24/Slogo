package slogo.Node.Commands.avatar;

import slogo.Geometry.Vector;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class ForwardCommand extends Command {
    public ForwardCommand() {
        this.setNumParameters(1);
    }
    public NodeValue execute() {
        checkContext();
        try {
            double distance = getChild(0).execute().getNumeric();

            double x=model.getAvatarX(), y=model.getAvatarY(), rotation=model.getAvatarRotation();

            Vector v = Vector.vectorFromCoord(x, y);
            Vector disp = Vector.vectorFromRadial(distance, rotation);

            Vector result = v.add(disp);

            model.setAvatarPosition(result.getX(), result.getY());

            return new NodeValue(distance);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
