package slogo.Node.Commands.multiple;

import slogo.Float.Precision;
import slogo.Node.Node;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeCategories.Group;
import slogo.Node.NodeValue;

import java.util.ArrayList;
import java.util.List;

public class TellCommand extends Command {

    public TellCommand() {
        this.setNumArguments(2);
        this.setPlural(true);
    }

    public NodeValue execute() throws Exception {
        checkContext();

        List<Integer> toBeActive = new ArrayList<Integer>();

        Group group = (Group) getChild(0);

        int lastID = 0;

        for (Node child: group.getChildren()) {
            lastID = Precision.round(child.execute().getNumeric());
            toBeActive.add(lastID);
        }

        model.setActiveAvatars(toBeActive);

        return new NodeValue(lastID);
    }
}
