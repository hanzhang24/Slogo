package slogo.Float;

import java.util.ResourceBundle;

public class Precision {
    private static double precision;
    private final static String resourcePath = "Float";
    private final static String resourcePackage = "Precision";
    private final static String precisionKey = "precision";
    private static boolean initialized = false;

    public static boolean lessEqual(double a, double b) {
        if (!Precision.initialized) {Precision.init();}
        return !(a > b + precision);
    }
    public static double asDouble(boolean bool) {
        if (!Precision.initialized) {Precision.init();}
        if (bool) return 1;
        else return 0;
    }

    public static int round(double val) {
        return (int) Math.round(val);
    }

    public static int floor(double val) {
        return (int) Math.floor(val + precision);
    }

    public static int ceil(double val) {
        return (int) Math.ceil(val - precision);
    }

    public static boolean asBoolean(double value) {
        if (!Precision.initialized) {Precision.init();}
        return Math.abs(value) > precision;
    }
    private static void init() {
        ResourceBundle resource = ResourceBundle.getBundle(resourcePath+"."+resourcePackage);
        Precision.precision = Double.parseDouble(resource.getString(precisionKey));
        Precision.initialized=true;
    }
}
