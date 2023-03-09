package slogo.Node.NodeCategories;

import slogo.Node.Node;
import slogo.Node.NodeValue;

public class Constant extends Node {
    NodeValue value;
    public Constant() {}

    @Override
    public Node deepClone() {
        return this;
    }
    public Constant(String str) {
        this.value = new NodeValue(str);
    }

    public Constant(double value) {
        this.value = new NodeValue(value);
    }

    public boolean hasCompatibleNumChildren() {
        return this.getChildren().size() == 1;
    }

    public NodeValue execute() {
        return this.value;
    }
}
