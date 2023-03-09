package slogo.Node.NodeCategories;

import slogo.Node.Node;

public abstract class Command extends Node {

    private int numArguments = 0;
    public int getNumArguments() {
        return numArguments;
    }

    public boolean hasCompatibleNumChildren() {
        // This will be overridden in some children

        int args = getNumArguments();
        int numChildren = getChildren().size();

        if (args == 0 && numChildren > 0) return false;
        return numChildren % args == 0 && numChildren >= args;
    }
    protected void setNumArguments(int numArguments){
        this.numArguments = numArguments;
    }
}
