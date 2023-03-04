package slogo.Node.NodeCategories;

import slogo.Node.Node;

public abstract class Command extends Node {

    private int numParameters = 0;
    public int getNumParameters() {
        return numParameters;
    }
    protected void setNumParameters(int numParameters){
        this.numParameters = numParameters;
    }
}
