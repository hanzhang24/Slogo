package slogo.Node.Commands.math;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class ProductCommand extends Command {

    public ProductCommand() {

        this.setNumArguments(2);
        this.setSupportsArbitraryNumArgs(true);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            double result = 1;
            int size = getChildren().size();
            for (int i = 0; i < size; i++) {
                double arg = getChild(i).execute().getNumeric();
                result *= arg;
            }
            return new NodeValue(result);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
