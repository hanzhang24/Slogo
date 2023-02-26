package Node;

import Model.*;
import Parser.CommandManager;
import org.w3c.dom.Document;

import javax.naming.NoInitialContextException;
import java.util.List;

public abstract class Node {
    protected List<Node> children;

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
    protected Node getChild(int ith) {
        return this.children.get(ith);
    }
    protected List<Node> getChildren() {
        return this.children;
    }
    public void addChild(Node node) {
        this.children.add(node);
    }
    public abstract NodeValue execute();
    public static void setLanguage(Document languageDoc) {

    };

}
