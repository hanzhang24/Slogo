package slogo.Node.NodeCategories;

import slogo.Node.Node;
import slogo.Node.NodeValue;

import java.util.ArrayList;
import java.util.List;

public class Root extends Node {
    private List<NodeValue> returnValues = new ArrayList<NodeValue>();
    public NodeValue execute() {
        NodeValue result = new NodeValue();
        for (Node child: getChildren()) {
            result = child.execute();
            returnValues.add(result);
        }
        return result;
    }

    public List<NodeValue> getReturnValues(){
        return returnValues;
    }
}