package slogo.Node.Commands.control;

import slogo.Node.Node;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeCategories.Group;
import slogo.Node.NodeCategories.Variable;
import slogo.Node.NodeValue;

public class UserFunctionCommand extends Command {
    // 1st child: group node with children nodes as variables
    // 2nd child: deepcopy of body
    // 3rd child: group node with passed values as nodes

    private static final int ARGUMENTS = 0;
    private static final int BODY = 1;
    private static final int OFFSET = 2;

    public UserFunctionCommand() {}
    public UserFunctionCommand(Group arguments, Group body) {
        this.setNumArguments(arguments.getChildren().size());
        this.addChild(arguments);
        this.addChild(body);
    }
    @Override
    public NodeValue execute() throws Exception {
        // set the variables to the passed values
        int numArguments = this.getNumArguments();

        for (int i = 0; i < numArguments; i++) {
            double argumentValue = getChild(OFFSET + i).execute().getNumeric();
            String argumentName = ((Variable) (getChild(ARGUMENTS).getChild(i))).getName();
            model.setUserVariable(argumentName, argumentValue);
        }
        // execute the body
        NodeValue result = getChild(BODY).execute();

        return result;
    }
}
