package slogo.Node.NodeCategories;
import slogo.Node.Node;
import slogo.Node.NodeValue;

import java.util.List;

public class Group extends Node {
    public NodeValue execute() throws Exception {
        checkContext();
        try {
            NodeValue result = new NodeValue(0);
            List<Node> children = this.getChildren();
            for (Node child: children) {
                result = child.execute();
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean hasCompatibleNumChildren() {
        return true;
    }
}
