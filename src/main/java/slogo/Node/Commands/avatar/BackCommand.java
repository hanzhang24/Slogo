package slogo.Node.Commands.avatar;

import slogo.Node.NodeValue;

public class BackCommand {
    public int getNumParameters() {return 1;}

    public NodeValue execute() {
        checkContext();
        try {
            NodeValue distance = getChild(0).execute();
            model.forward(distance.getNumeric());
            return distance;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
