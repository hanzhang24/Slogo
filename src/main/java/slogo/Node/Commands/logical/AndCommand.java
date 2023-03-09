package slogo.Node.Commands.logical;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class AndCommand extends Command {
    public AndCommand(){
        this.setNumArguments(2);
        this.setSupportsArbitraryNumArgs(true);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        try {
            boolean result = true;
            int size = getChildren().size();
            for (int i = 0; i < size; i++) {
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
