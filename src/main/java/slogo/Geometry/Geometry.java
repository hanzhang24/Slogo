package slogo.Geometry;
import java.lang.Math;

public interface Geometry {
    public static double getFullRotationDeg(double rotation) {
        return ((int) (rotation/360)) * 360;
    }

    public static double getResidualRotationDeg(double rotation) {
        return rotation - getFullRotationDeg(rotation);
    }

}
