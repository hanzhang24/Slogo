package slogo.Node;

import slogo.Node.Commands.control.UserFunctionCommand;
import slogo.Node.NodeCategories.Group;

public class UserFunctionTemplate {

    String name;
    Group parameters, body;
    public UserFunctionTemplate(String name, Group parameters, Group body) throws Exception {
        this.name = name.toLowerCase();
        this.parameters = (Group) parameters.deepClone();
        this.body = (Group) body.deepClone();
    }

    public String getName() {
        return name;
    }
    public UserFunctionCommand makeInstance() throws Exception {
        UserFunctionCommand command = new UserFunctionCommand((Group) parameters.deepClone(), (Group) body.deepClone());
        return command;
    }
}
