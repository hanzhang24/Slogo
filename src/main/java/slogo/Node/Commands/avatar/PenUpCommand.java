package slogo.Node.Commands.avatar;

import slogo.Node.Command;
import slogo.Node.Node;
import slogo.Node.NodeValue;

public class PenUpCommand extends Command {
    public int getNumParameters() {return 0;}
    public NodeValue execute() {
        checkContext();
        try {
            // TODO: This will need be modified when model is changed

            return new NodeValue(0);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
