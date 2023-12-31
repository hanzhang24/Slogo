package slogo.Node.NodeCategories;

import slogo.Node.Node;
import slogo.Node.NodeValue;

public class Variable extends Node {

    private String name;

    @Override
    public Node deepClone() {
        return this;
    }
    public Variable() {};
    public Variable(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public boolean hasCompatibleNumChildren() {
        return this.getChildren().size() == 0;
    }
    public NodeValue getValue() throws Exception {
        checkContext();
        return new NodeValue(model.getUserVariable(this.name));
    }
    public NodeValue execute() throws Exception {
        return getValue();
    }
}
