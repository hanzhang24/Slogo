package slogo.Node.Commands.math;


import slogo.Node.Command;
import slogo.Node.NodeValue;

public class AddCommand extends Command {
    public int getNumParameters() {return 2;}
    public NodeValue execute() {
        checkContext();
        try {
            // TODO: This will need be modified when model is changed
            double arg1 = getChild(0).execute().getNumeric();
            double arg2 = getChild(1).execute().getNumeric();
            return new NodeValue(arg1 + arg2);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
