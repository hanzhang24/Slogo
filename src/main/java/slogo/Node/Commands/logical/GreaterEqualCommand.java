package slogo.Node.Commands.logical;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class GreaterEqualCommand extends Command {

    public GreaterEqualCommand(){
        this.setNumParameters(2);
    }
    public NodeValue execute() {
        checkContext();
        try {
            double arg_1 = getChild(0).execute().getNumeric();
            double arg_2 = getChild(1).execute().getNumeric();

            boolean result = Precision.lessEqual(-arg_1, -arg_2);

            return new NodeValue(Precision.asDouble(result));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
