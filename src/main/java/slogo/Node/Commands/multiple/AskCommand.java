package slogo.Node.Commands.multiple;

import slogo.Float.Precision;
import slogo.Node.Node;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeCategories.Group;
import slogo.Node.NodeValue;

import java.util.ArrayList;
import java.util.List;

public class AskCommand extends Command {

    public AskCommand() {
        this.setNumArguments(2);
        this.setPlural(true);
    }
    public NodeValue execute() throws Exception {
        checkContext();

        Group group = (Group) getChild(0);
        List<Integer> toBeActive = new ArrayList<Integer>();
        for (Node child: group.getChildren()) {
            int curID = Precision.round(child.execute().getNumeric());
            toBeActive.add(curID);
        }

        List<Integer> prevActive = model.getActiveAvatars();
        model.setActiveAvatars(toBeActive); // This is to flush the model to force creation of unseen avatars
        model.setActiveAvatars(prevActive);

        int prevCurrentID = model.getCurrentAvatarID();
        NodeValue result = new NodeValue(0);

        for (Integer curID: toBeActive) {
            model.setCurrentAvatarID(curID);
            result = getChild(1).execute();
        }

        model.setCurrentAvatarID(prevCurrentID);

        return result;
    }

}
