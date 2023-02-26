package slogo.Node.Commands.avatar;

import slogo.Geometry.Vector;
import slogo.Node.Command;
import slogo.Node.NodeValue;

public class BackCommand extends Command {
    public int getNumParameters() {return 1;}

    public NodeValue execute() {
        checkContext();
        try {
            double distance = getChild(0).execute().getNumeric();
            // TODO: This will need be modified when model is
            // Get x, y, rotation
            double x=3, y=4, rotation = Math.PI / 4;
            // END TODO

            Vector v = Vector.vectorFromCoord(x, y);
            Vector disp = Vector.vectorFromRadial(distance, rotation);

            Vector result = v.subtract(disp);

            model.setAvatarPosition(result.getX(), result.getY());

            return new NodeValue(distance);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
