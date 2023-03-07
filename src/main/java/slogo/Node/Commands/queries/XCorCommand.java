package slogo.Node.Commands.queries;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class XCorCommand extends Command {
    public XCorCommand() {
        this.setNumArguments(0);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        return new NodeValue(model.getAvatarX());
    }
}