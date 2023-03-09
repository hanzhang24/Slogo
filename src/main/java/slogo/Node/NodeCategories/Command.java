package slogo.Node.NodeCategories;

import slogo.Node.Node;

public abstract class Command extends Node {

    private int numArguments = 0;
    private boolean supportsArbitraryNumArgs = false;
    public int getNumArguments() {
        return numArguments;
    }
    protected void setNumArguments(int numArguments){
        this.numArguments = numArguments;
    }
    public boolean getSupportsArbitraryNumArgs() {
        return supportsArbitraryNumArgs;
    }
    protected void setSupportsArbitraryNumArgs(boolean value) {
        this.supportsArbitraryNumArgs = value;
    }

    public boolean hasCompatibleNumChildren() {
        // This will be overridden in some children
        int numChildren = getChildren().size();
        if (getSupportsArbitraryNumArgs()) return numChildren >= 2;
        return numChildren == getNumArguments();
    }

}
