package slogo.Node.Commands.math;

import slogo.Node.Command;
import slogo.Node.NodeValue;
import java.util.Random;

public class RandomCommand extends Command {
    public int getNumParameters() {return 1;}
    public NodeValue execute() {
        checkContext();
        try {
            // TODO: This will need be modified when model is changed

            double upper = getChild(0).execute().getNumeric();

            Random rand = new Random();
            double randomNum = rand.nextInt((int) upper + 1);

            return new NodeValue(randomNum);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
