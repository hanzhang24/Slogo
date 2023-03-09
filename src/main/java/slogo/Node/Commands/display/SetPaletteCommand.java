package slogo.Node.Commands.display;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class SetPaletteCommand extends Command {

    public SetPaletteCommand() {
        this.setNumArguments(4);
    }

    public NodeValue execute() throws Exception {
        throw new RuntimeException("Set palette not implemented by Model API");
    }
}
