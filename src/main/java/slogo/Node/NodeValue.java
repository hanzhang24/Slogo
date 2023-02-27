package slogo.Node;

public class NodeValue {
    private String string;
    private double numeric;

    public NodeValue() {
        this.string = "";
        this.numeric = 0;
    }
    public NodeValue(String string) {
        this.string = string;
        this.numeric = 0;
    }

    public NodeValue(double numeric) {
        this.string = "";
        this.numeric = numeric;
    }

    public String getString() {
        return this.string;
    }

    public double getNumeric() {
        return this.numeric;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setNumeric(double numeric) {
        this.numeric = numeric;
    }

}
