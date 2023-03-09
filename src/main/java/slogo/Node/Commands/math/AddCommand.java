package slogo.Node.Commands.math;


import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class AddCommand extends Command {
    public AddCommand() {

        this.setNumArguments(2);
        this.setSupportsArbitraryNumArgs(true);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {

            double result = 0;
            int size = getChildren().size();
            for (int i = 0; i < size; i++) {
                double arg = getChild(i).execute().getNumeric();
                result += arg;
            }

            return new NodeValue(result);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
