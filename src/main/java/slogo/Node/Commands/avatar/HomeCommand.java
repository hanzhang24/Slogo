package slogo.Node.Commands.avatar;

import slogo.Geometry.Vector;
import slogo.Node.Command;
import slogo.Node.Node;
import slogo.Node.NodeValue;

public class HomeCommand extends Command {

    public int getNumParameters() {return 0;}
    public NodeValue execute() {
        checkContext();
        try {
            // TODO: This will need be modified when model is
            // Get x, y, rotation
            double x=3, y=4, rotation = Math.PI / 4;
            // END TODO

            Vector v = Vector.vectorFromCoord(x, y);

            model.setPosition(0, 0);

            return new NodeValue(v.getLength());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
