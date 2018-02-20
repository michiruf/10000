package com.github.tenthousand.bots.chrisiruf_zerstoerer;

class MyMath {

    /** Allowed calculation error. */
    public static final double RC_EPS = 1.0e-6;

    /**
     * Helper to check if a value is integer with respect to the allowed calculation error.
     */
    public static boolean isInteger(double val) {
        long i = Math.round(val);
        if (i - RC_EPS <= val && val <= i + RC_EPS) {
            return true;
        }
        return false;
    }

    /**
     * Check if equal with respect to calculation error.
     */
    public static boolean eqWithTolerance(double a, double b) {
        if (b - RC_EPS <= a && a <= b + RC_EPS) {
            return true;
        }
        return false;
    }

    public static double round(double val, double decimals) {
        return (double) Math.round(val * Math.pow(10, decimals)) / Math.pow(10, decimals);

    }

    public static double getEuclidianDistance(double px, double py, double qx, double qy) {
        return Math.sqrt(Math.pow(py - qy, 2) + (Math.pow(px - qx, 2)));
    }

    public static int max0(int b) {
        return Math.max(0, b);
    }

    public static double max0(double b) {
        return Math.max(0.0, b);
    }

    public static int min(int x, int y, int z) {
        return Math.min(Math.min(x, y), z);
    }

    public static int max(int x, int y, int z) {
        return Math.max(Math.max(x, y), z);
    }

    public static double min(double x, double y, double z) {
        return Math.min(Math.min(x, y), z);
    }

    public static double max(double x, double y, double z) {
        return Math.max(Math.max(x, y), z);
    }

    /** Rosenbrock (1960), Spall (2003) */
    public static double rosenbrockFunction(double[] t) {
        if (t.length % 2 != 0) throw new Error();
        double ret = 0.0;
        for (int i = 0; i < t.length / 2; i++) {
            ret += 100.0 * Math.pow(t[2 * i + 1] - Math.pow(t[2 * i], 2), 2) + Math.pow(1.0 - t[2 * i], 2);
        }
        return ret;
    }

}
