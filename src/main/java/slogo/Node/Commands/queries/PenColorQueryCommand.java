package slogo.Node.Commands.queries;

import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class PenColorQueryCommand extends Command {

    public PenColorQueryCommand() {
        this.setNumArguments(0);
    }

    public NodeValue execute() throws Exception {

        throw new Exception("Pen color query not implemented by model API");
    }
}
