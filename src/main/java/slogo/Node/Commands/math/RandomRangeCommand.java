package slogo.Node.Commands.math;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

import java.util.Random;

public class RandomRangeCommand extends Command {

    public RandomRangeCommand() {
        this.setNumParameters(2);
    }
    public NodeValue execute() {
        checkContext();
        try {

            double arg_lower = getChild(0).execute().getNumeric();
            double arg_upper = getChild(1).execute().getNumeric();

            int lower = Precision.ceil(arg_lower);
            int upper = Precision.floor(arg_upper);

            Random rand = new Random();

            int result = rand.nextInt((upper-lower)+1)+lower;

            return new NodeValue((double) result);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
