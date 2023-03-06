package slogo.Node.Commands.logical;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class NotCommand extends Command {

    public NotCommand(){
        this.setNumArguments(1);
    }
    public NodeValue execute() {
        checkContext();
        try {
            double arg = getChild(0).execute().getNumeric();

            boolean result = !Precision.asBoolean(arg);

            return new NodeValue(Precision.asDouble(result));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
