package Node;

public class Root extends Node {
    private int numParams = 0;
    public NodeValue execute() {
        NodeValue result = new NodeValue();
        for (Node child: getChildren()) {
            result = child.execute();
        }
        return result;
    }
}