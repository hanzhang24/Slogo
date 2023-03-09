package slogo.Node.NodeCategories;

import slogo.Node.Node;
import slogo.Node.NodeValue;

import java.util.ArrayList;
import java.util.List;

public class Root extends Node {
    private List<Double> returnValues = new ArrayList<Double>();
    public NodeValue execute() throws Exception {

        checkContext();

        returnValues = new ArrayList<Double>();

        NodeValue result = new NodeValue();
        for (Node child: getChildren()) {
            List<Integer> activeAvatars = model.getActiveAvatars();
            if (child.getPlural()) {
                result = child.execute();
                returnValues.add(result.getNumeric());
            } else {
                for (Integer avatarID: activeAvatars) {
                    model.setCurrentAvatarID(avatarID);
                    result = child.execute();
                }
                returnValues.add(result.getNumeric());
            }
        }
        return result;
    }
    public boolean hasCompatibleNumChildren() {
        return true;
    }
    public List<Double> getReturnValues(){
        return returnValues;
    }
}