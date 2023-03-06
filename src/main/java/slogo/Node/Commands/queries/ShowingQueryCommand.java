package slogo.Node.Commands.queries;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class ShowingQueryCommand extends Command {

    public ShowingQueryCommand() {
        this.setNumParameters(0);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        boolean isShowing = model.getAvatarVisible();
        double result = Precision.asDouble(isShowing);
        return new NodeValue(result);
    }

}
