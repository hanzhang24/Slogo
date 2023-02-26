package Node;

import Parser.Tokenizer;
import org.w3c.dom.Document;

import java.util.List;

public abstract class Node {
    List<Node> children;
    protected abstract Node getChild(int ith);

    protected abstract int getNumParams();
    public abstract double execute();
    public static void setLanguage(Document languageDoc) {

    };

}
