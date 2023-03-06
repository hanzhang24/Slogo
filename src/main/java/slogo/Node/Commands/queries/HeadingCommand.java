package slogo.Node.Commands.queries;

import slogo.Geometry.Geometry;
import slogo.Node.NodeCategories.Command;
import slogo.Node.NodeValue;

public class HeadingCommand extends Command {
    public HeadingCommand() {
        this.setNumParameters(0);
    }

    public NodeValue execute() throws Exception {
        checkContext();
        double rotation = model.getAvatarRotation();
        double residual = Geometry.getResidualRotationDeg(rotation);
        if (residual < 0) residual += 360;

        return new NodeValue(residual);
    }

}
