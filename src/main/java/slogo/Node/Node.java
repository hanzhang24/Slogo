package slogo.Node;

import slogo.Model.Model;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected List<Node> children = new ArrayList<Node>();
    private boolean hasContext = false, plural = false;
    protected Model model;
    public void initContext(Model model) {
        this.hasContext = true;
        this.model = model;
        for (Node child: children)
            child.initContext(model);
    }
    protected void checkContext() throws Exception {
        if (!this.hasContext) {
            throw new Exception("Model not attached to node");
        } else if (!hasCompatibleNumChildren()) {
            throw new Exception(this.getClass() + " did not get an appropriate num of children, got " + getChildren().size() + " children");
        }
    }
    public Node deepClone() throws Exception {
        Node selfNode = this.getClass().newInstance();
        for (Node child: this.getChildren()) {
            selfNode.addChild(child.deepClone());
        }
        return selfNode;
    }
    public Node getChild(int ith) {
        return this.children.get(ith);
    }
    public List<Node> getChildren() {
        return this.children;
    }

    public boolean getPlural() {return plural;}
    protected void setPlural(boolean bool) {this.plural = bool;}

    public abstract boolean hasCompatibleNumChildren();
    public void addChild(Node node) {
        this.children.add(node);
    }
    public abstract NodeValue execute() throws Exception;
    public static void setLanguage(Document languageDoc) {

    };

}
