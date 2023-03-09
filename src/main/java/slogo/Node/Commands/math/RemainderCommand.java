package slogo.Node.Commands.math;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class RemainderCommand extends Command {

    public RemainderCommand() {
        this.setNumArguments(2);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            double dividend = getChild(0).execute().getNumeric();
            double divisor = getChild(1).execute().getNumeric();

            double remainder = dividend - (int)(dividend/divisor) * divisor;

            return new NodeValue(remainder);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
