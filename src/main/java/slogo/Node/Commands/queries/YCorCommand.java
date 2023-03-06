package slogo.Node.Commands.queries;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;
import slogo.Parser.CommandManager;

public class YCorCommand extends Command {
    public YCorCommand() {
        this.setNumParameters(0);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        return new NodeValue(model.getAvatarY());
    }
}
