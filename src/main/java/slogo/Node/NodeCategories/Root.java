package slogo.Node.NodeCategories;

import slogo.Node.Node;
import slogo.Node.NodeValue;

import java.util.ArrayList;
import java.util.List;

public class Root extends Node {
    private List<Double> returnValues = new ArrayList<Double>();
    public NodeValue execute() throws Exception {
        NodeValue result = new NodeValue();
        for (Node child: getChildren()) {
            result = child.execute();
            returnValues.add(result.getNumeric());
        }
        return result;
    }
    public List<Double> getReturnValues(){
        return returnValues;
    }
}