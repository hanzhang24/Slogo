package slogo.Node.Commands.avatar;

import slogo.Geometry.Vector;
import slogo.Node.Command;
import slogo.Node.Node;
import slogo.Node.NodeValue;

public class PenDownCommand extends Command {
    public int getNumParameters() {return 0;}
    public NodeValue execute() {
        checkContext();
        try {
            // TODO: This will need be modified when model is changed
            // model.penDown()

            return new NodeValue(1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
