package slogo.Geometry;
import java.lang.Math;

public class Vector {
    private double x, y;

    private Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public static Vector vectorFromCoord(double x, double y) {
        return new Vector(x, y);
    }

    public static Vector vectorFromRadial(double distance, double rotation) {
        double rotRad = Math.toRadians(rotation);
        return new Vector(distance*Math.cos(rotRad), distance*Math.sin(rotRad));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength() {
        return Math.sqrt(x*x + y*y);
    }

    public double getRotationDeg() {
        return Math.toDegrees(Math.atan2(y, x));
    };
    public double getRotationRad() {
        return Math.atan2(y, x);
    }
    public Vector normalized() {
        double length = this.getLength();
        if (length == 0) {
            // TODO Make this language specific.
            throw new ArithmeticException("Cannot normalize vector of length 0");
        }
        return new Vector(this.x/length, this.y/length);
    }
    public Vector substract(Vector v) {
        return new Vector(this.x - v.getX(), this.y - v.getY());
    }
    public Vector add(Vector v) {
        return new Vector(this.x + v.getX(), this.y + v.getY());
    }
}
