package slogo.Node.Commands.math;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;
import java.util.Random;

public class RandomCommand extends Command {
    public RandomCommand() {
        this.setNumArguments(1);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            Random rand = new Random();
            double arg_upper = getChild(0).execute().getNumeric();
            int upper = Precision.floor(arg_upper);
            int result = rand.nextInt(upper + 1);
            return new NodeValue((double) result);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
