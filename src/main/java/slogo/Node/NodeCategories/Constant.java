package slogo.Node.NodeCategories;

import slogo.Node.Node;
import slogo.Node.NodeValue;

public class Constant extends Node {
    NodeValue value = new NodeValue(0);
    public Constant() {}
    public Constant(String str) {
        this.value = new NodeValue(Double.parseDouble(str));
    }

    public Constant(double value) {
        this.value = new NodeValue(value);
    }

    public NodeValue execute() {
        return this.value;
    }
}
