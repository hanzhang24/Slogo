package slogo.Node.NodeCategories;

import slogo.Node.Node;

public abstract class Command extends Node {

    private int numArguments = 0;
    public int getNumArguments() {
        return numArguments;
    }
    protected void setNumArguments(int numArguments){
        this.numArguments = numArguments;
    }
}
