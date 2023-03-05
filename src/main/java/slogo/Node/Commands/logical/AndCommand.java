package slogo.Node.Commands.logical;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class AndCommand extends Command {
    public AndCommand(){
        this.setNumParameters(2);
    }
    public NodeValue execute() {
        checkContext();
        try {
            boolean result = true;

            for (int i = 0; i < this.getNumParameters(); i++) {
                double arg = getChild(i).execute().getNumeric();
                boolean arg_bool = Precision.asBoolean(arg);
                result &= arg_bool;
                if (!result) break;
            }

            double resultDouble = Precision.asDouble(result);

            return new NodeValue(resultDouble);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
