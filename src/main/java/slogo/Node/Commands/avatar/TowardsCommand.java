package slogo.Node.Commands.avatar;

import slogo.Geometry.Geometry;
import slogo.Geometry.Vector;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class TowardsCommand extends Command {
    public TowardsCommand() {
        this.setNumParameters(2);
    }
    public NodeValue execute() {
        checkContext();
        try {
            double targetX = getChild(0).execute().getNumeric();
            double targetY = getChild(1).execute().getNumeric();

            double currentX = model.getAvatarX(), currentY = model.getAvatarY();

            Vector dest = Vector.vectorFromCoord(targetX, targetY);
            Vector current = Vector.vectorFromCoord(currentX, currentY);

            Vector delta = dest.subtract(current);

            double targetRotationResidual = Geometry.getResidualRotationDeg(delta.getRotationDeg());
            double currentRotation = model.getAvatarRotation();

            double currentBaseRotation = Geometry.getFullRotationDeg(currentRotation);

            double targetRotation = targetRotationResidual + currentBaseRotation;

            double deltaRotation = targetRotation - currentRotation;

            model.setAvatarRotation(targetRotation);

            return new NodeValue(deltaRotation);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
