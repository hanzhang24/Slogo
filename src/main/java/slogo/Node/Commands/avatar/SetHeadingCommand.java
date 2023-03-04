package slogo.Node.Commands.avatar;

import slogo.Geometry.Geometry;
import slogo.Geometry.Vector;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class SetHeadingCommand extends Command {
    public SetHeadingCommand() {
        this.setNumParameters(1);
    }
    public NodeValue execute() {
        checkContext();
        try {
            double targetRotation = getChild(0).execute().getNumeric();
            double currentRotation = model.getAvatarRotation();
            double resultRotation = Geometry.getFullRotationDeg(currentRotation) + Geometry.getResidualRotationDeg(targetRotation);
            double delta = resultRotation - currentRotation;
            model.setAvatarRotation(resultRotation);
            return new NodeValue(delta);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}