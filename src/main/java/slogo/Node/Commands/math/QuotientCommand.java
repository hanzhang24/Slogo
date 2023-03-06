package slogo.Node.Commands.math;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class QuotientCommand extends Command {

    public QuotientCommand() {
        this.setNumArguments(2);
    }
    public NodeValue execute() {
        checkContext();
        try {
            double numerator = getChild(0).execute().getNumeric();
            double denominator = getChild(1).execute().getNumeric();
            return new NodeValue(numerator/denominator);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
