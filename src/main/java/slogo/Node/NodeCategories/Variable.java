package slogo.Node.NodeCategories;

import slogo.Node.Node;
import slogo.Node.NodeValue;

public class Variable extends Node {

    private String name;

    public Variable(String name) {
        this.name = name;
    }
    public NodeValue execute() {
        checkContext();
        return new NodeValue(model.getUserVariable(this.name));
    }
}
