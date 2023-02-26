package Node;

import Parser.Tokenizer;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    private List<Node> children;
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
