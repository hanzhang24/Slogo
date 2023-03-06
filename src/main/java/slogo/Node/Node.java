package slogo.Node;

import slogo.Model.Model;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected List<Node> children = new ArrayList<Node>();
    protected boolean hasContext = false;
    protected Model model;
    public void initContext(Model model) {
        this.hasContext = true;
        this.model = model;
        for (Node child: children)
            child.initContext(model);
    }
    protected void checkContext() {
        if (!this.hasContext) {
            throw new NullPointerException("Model not attached to node");
        }
    }

    public Node deepClone() throws Exception {
        Node selfNode = this.getClass().newInstance();
        for (Node child: this.getChildren()) {
            selfNode.addChild(child.deepClone());
        }
        return this.getClass().cast(selfNode);
    }
    public Node getChild(int ith) {
        return this.children.get(ith);
    }
    public List<Node> getChildren() {
        return this.children;
    }
    public void addChild(Node node) {
        this.children.add(node);
    }
    public abstract NodeValue execute() throws Exception;
    public static void setLanguage(Document languageDoc) {

    };

}
