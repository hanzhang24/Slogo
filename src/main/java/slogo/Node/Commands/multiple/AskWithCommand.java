package slogo.Node.Commands.multiple;

import slogo.Float.Precision;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class AskWithCommand extends Command {

    public AskWithCommand() {
        this.setNumArguments(2);
        this.setPlural(true);
    }

    public NodeValue execute() throws Exception {
        checkContext();

        int totAvatars = model.getTotalNumberOfAvatars();

        NodeValue result = new NodeValue(0);

        int prevCurrentAvatarID = model.getCurrentAvatarID();

        for(int i = 1; i <= totAvatars; i++) {
            model.setCurrentAvatarID(i);
            if (Precision.asBoolean(getChild(0).execute().getNumeric())) {
                result = getChild(1).execute();
            }
        }

        model.setCurrentAvatarID(prevCurrentAvatarID);

        return result;
    }
}
