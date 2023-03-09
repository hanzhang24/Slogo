package slogo.Node.Commands.math;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class LogCommand extends Command {

    public LogCommand() {
        this.setNumArguments(1);
    }
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            double arg = getChild(0).execute().getNumeric();
            return new NodeValue(Math.log(arg));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
