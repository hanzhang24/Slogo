package slogo.Node.Commands.display;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class SetPenSizeCommand extends Command {
    public SetPenSizeCommand() {
        this.setNumArguments(1);
    }

    public NodeValue execute() throws Exception {
        throw new RuntimeException("Set pen size not implemented by Model API");
    }
}
