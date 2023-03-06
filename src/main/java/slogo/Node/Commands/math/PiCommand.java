package slogo.Node.Commands.math;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class PiCommand extends Command {

    public PiCommand() {
        this.setNumArguments(0);
    }
    public NodeValue execute() {
        checkContext();
        try {
            return new NodeValue(Math.PI);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
