package com.github.tenthousand.bots.chrisiruf_zerstoerer.util;

import java.util.ArrayList;

public class AH {

    public static String toString(Object[] a, String startDelim, String delim, String endDelim) {
        if (a == null) return "null";
        if (a.length == 0) return startDelim + endDelim;
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(a[i].toString()).append(delim);
        }
        return builder.append(a[a.length - 1]).append(endDelim).toString();
    }

    public static String toString(int[] a, String startDelim, String delim, String endDelim) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(a[i]).append(delim);
        }
        return builder.append(a[a.length - 1]).append(endDelim).toString();
    }

    public static String toString(double[] a, String startDelim, String delim, String endDelim) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(a[i]).append(delim);
        }
        return builder.append(a[a.length - 1]).append(endDelim).toString();
    }

    public static String toString(boolean[] a, String startDelim, String delim, String endDelim) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(a[i]).append(delim);
        }
        return builder.append(a[a.length - 1]).append(endDelim).toString();
    }

    public static String toString(Object[][] a, String startDelim, String delim, String endDelim,
            String startDelim2, String delim2, String endDelim2) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(toString(a[i], startDelim2, delim2, endDelim2)).append(delim);
        }
        return builder.append(toString(a[a.length - 1], startDelim2, delim2, endDelim2)).append(endDelim)
                .toString();
    }

    public static String toString(int[][] a, String startDelim, String delim, String endDelim,
            String startDelim2, String delim2, String endDelim2) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(toString(a[i], startDelim2, delim2, endDelim2)).append(delim);
        }
        return builder.append(toString(a[a.length - 1], startDelim2, delim2, endDelim2)).append(endDelim)
                .toString();
    }

    public static String toString(double[][] a, String startDelim, String delim, String endDelim,
            String startDelim2, String delim2, String endDelim2) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(toString(a[i], startDelim2, delim2, endDelim2)).append(delim);
        }
        return builder.append(toString(a[a.length - 1], startDelim2, delim2, endDelim2)).append(endDelim)
                .toString();
    }

    public static String toString(boolean[][] a, String startDelim, String delim, String endDelim,
            String startDelim2, String delim2, String endDelim2) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(toString(a[i], startDelim2, delim2, endDelim2)).append(delim);
        }
        return builder.append(toString(a[a.length - 1], startDelim2, delim2, endDelim2)).append(endDelim)
                .toString();
    }

    public static String toString(boolean[][] a) {
        String ret = "";
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j]) ret += "(" + i + "," + j + "),";
            }
        }
        return ret;
    }

    public static String toTableString(String rowHeader, int[] rowNames, String columnHeader, int[] colNames,
            int[][] values) {
        String ret = rowHeader + "\\" + columnHeader + "|\t";
        int headerLength = rowHeader.length() + 1 + columnHeader.length();

        for (int i : colNames) {
            ret += String.format("%04d", i) + "\t";
        }
        ret += "\n";

        for (int i = 0; i < rowNames.length; i++) {
            ret += String.format("%04d", rowNames[i]);
            for (int k = 0; k < headerLength - 4; k++) {
                ret += " ";
            }
            ret += "|\t";

            for (int j = 0; j < values[i].length; j++) {
                ret += String.format("%04d", values[i][j]) + "\t";
            }
            ret += "\n";
        }
        return ret;
    }

    /* parse */

    /**
     * @param str
     *            [x, ... , y] or x, ... , y
     * @return
     */
    public static int[] parse(String str) {
        if (str.length() == 0) return new int[0];
        if (str.charAt(0) == '[') {
            str = str.substring(1, str.length() - 1);
        }
        String[] s = str.split(",");
        int[] ret = new int[s.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Integer.parseInt(s[i].trim());
        }
        return ret;
    }

    public static double[] parseDouble(String str) {
        if (str.length() == 0) return new double[0];
        if (str.charAt(0) == '[') {
            str = str.substring(1, str.length() - 1);
        }
        String[] s = str.split(",");
        double[] ret = new double[s.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Double.parseDouble(s[i].trim());
        }
        return ret;
    }

    /* /parse */

    public static String[] subArray(String[] a, int first, int last) {
        String[] ret = (String[]) new String[last - first + 1];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = a[first + i];
        }
        return ret;
    }

    public static int[] subArray(int[] a, int first, int last) {
        int[] ret = new int[last - first + 1];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = a[first + i];
        }
        return ret;
    }

    /* Aggregates */

    public static double accumulate(double[] a) {
        return sum(a);
    }

    public static int sum(int[] a) {
        int result = 0;
        for (int i : a) {
            result += i;
        }
        return result;
    }

    public static double sum(double[] a) {
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i];
        }
        return result;
    }

    public static double avg(double[] a) {
        return sum(a) / a.length;
    }

    public static double expectation(double[] probs, double[] a) {
        assert (probs.length == a.length);
        double ret = 0.0;
        for (int i = 0; i < a.length; i++) {
            ret += probs[i] * a[i];
        }
        return ret;
    }

    public static double var(double[] probs, double[] vals) {
        double mu = expectation(probs, vals);
        double ret = 0.0;
        for (int i = 0; i < vals.length; i++) {
            ret += probs[i] * (vals[i] - mu) * (vals[i] - mu);
        }
        return ret;
    }

    public static double varOnlyPositive(double[] probs, double[] vals) {
        double mu = expectation(probs, vals);
        double ret = 0.0;
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] > mu) ret += probs[i] * (vals[i] - mu) * (vals[i] - mu);
        }
        return ret;

    }

    public static double max(double[] a) {
        double ret = -Double.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > ret) ret = a[i];
        }
        return ret;
    }

    public static int max(int[] a) {
        int ret = -Integer.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > ret) ret = a[i];
        }
        return ret;
    }

    public static double min(double[] a) {
        double ret = Double.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < ret) ret = a[i];
        }
        return ret;
    }

    public static int min(int[] a) {
        int ret = Integer.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < ret) ret = a[i];
        }
        return ret;
    }

    /* Norms */
    public static double maxNorm(double[] a) {
        double ret = -Double.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (Math.abs(a[i]) > ret) ret = Math.abs(a[i]);
        }
        return ret;
    }
    /* /Norms */

    /* scalar x vector --> vector */

    public static int[] scalarMult(int scalar, int[] vector) {
        int[] ret = new int[vector.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = vector[i] * scalar;
        }
        return ret;
    }

    public static double[] mult(double scalar, double[] vector) {
        double[] ret = new double[vector.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = vector[i] * scalar;
        }
        return ret;
    }

    /* / scalar x vector --> vector */

    /* vector x vetor --> vector */

    public static double[] sum(double[] a, double[] b) {
        if (a.length != b.length) throw new Error();
        double[] ret = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            ret[i] = a[i] + b[i];
        }
        return ret;
    }

    public static double[] minus(double[] a, double[] b) {
        if (a.length != b.length) throw new Error();
        double[] ret = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            ret[i] = a[i] - b[i];
        }
        return ret;
    }

    /* / vector x vetor --> vector */

    /**
     * @return sum of the multiplied elements of a and b.
     */
    public static double multiplyElementsAndAccumulate(double[] a, double[] b) {
        if (a.length != b.length) {
            System.err.println("ERROR in multiplyElementsAndSumUp(): arrays differ in length's");
        }
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    /**/
    public static boolean leq(int[] a, int[] b) {
        if (a.length != b.length) throw new Error();
        for (int i = 0; i < b.length; i++) {
            if (a[i] > b[i]) return false;
        }
        return true;
    }
    /**/

    /* Fill */

    public static void fill(int[][] a, int val) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = val;
            }
        }
    }

    /**
     * Create int-array filled integers counting up from min to max.
     * 
     * @param max
     *            inclusive
     */
    public static int[] fill(int min, int max) {
        int[] ret = new int[max - min + 1];
        for (int i = min; i <= max; i++) {
            ret[i] = i;
        }
        return ret;
    }

    public static boolean eqWithTolerance(double[] a, double[] b) {
        if (a == b) return true;
        if (a == null && b != null) return false;
        if (a != null && b == null) return false;
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!MyMath.eqWithTolerance(a[i], b[i])) return false;
        }
        return true;
    }

    /* Cloning */

    public static double[] clone(double[] a) {
        double[] ret = new double[a.length];
        System.arraycopy(a, 0, ret, 0, a.length);
        return ret;
    }

    public static int[] clone(int[] a) {
        int[] ret = new int[a.length];
        System.arraycopy(a, 0, ret, 0, a.length);
        return ret;
    }

    public static int[][] clone(int[][] a) {
        int[][] target = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            System.arraycopy(a[i], 0, target[i], 0, a[i].length);
        }
        return target;
    }

    /* Compare */

    public static boolean equal(int[][] a, int[][] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < b.length; i++) {
            if (a[i].length != b[i].length) return false;
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }

    /* Combinations */

    /**
     * 
     * @param a
     *            array with arrays that represent a n-tuple. For instance [[1][2]]
     * @param b
     *            array with elements. For instance [3,4]
     * @return array that contains all arrays of combinations of the elements in a and v. For instance
     *         [[1,3][1,4],[1,4],[2,4]].
     */
    public static int[][] combine(int[][] a, int[] b) {
        int[][] ret = new int[a.length * b.length][];
        int counter = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                int[] z = new int[a[i].length + 1];
                System.arraycopy(a[i], 0, z, 0, a[i].length);
                z[a[i].length] = b[j];
                ret[counter++] = z;
            }
        }
        return ret;
    }

    public static int[][] combine(int[][] a, int[][] b) {
        int[][] ret = new int[a.length * b.length][];
        int counter = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                int[] z = new int[a[i].length + b[j].length];
                System.arraycopy(a[i], 0, z, 0, a[i].length);
                System.arraycopy(b[j], 0, z, a[i].length, b[j].length);
                ret[counter++] = z;
            }
        }
        return ret;
    }

    public static int[][] wrap(int[] a) {
        int[][] ret = new int[a.length][];
        for (int i = 0; i < a.length; i++) {
            ret[i] = new int[] { a[i] };
        }
        return ret;
    }

    /**
     * Create all combinations of the sets of integer that are defined by the upper bounds, whereas the
     * lower bounds are all 0.
     * 
     * @param lb
     *            Lower bounds, for instance [0,0].
     * @param ub
     *            Upper bounds, for instance [1,2].
     * @return for instance [[0,0],[0,1],[0,2],[1,0],[1,1],[1,2]].
     */
    public static int[][] combineRecursive(int[] lb, int[] ub) {
        ArrayList<int[]> container = new ArrayList<int[]>();
        combineRecursive(new int[0], 0, lb, ub, container);
        return container.toArray(new int[container.size()][]);
    }

    private static void combineRecursive(int[] node, int i, int[] lb, int[] ub, ArrayList<int[]> leafs) {
        if (i == ub.length) {
            leafs.add(node);
            return;
        }
        for (int val = lb[i]; val <= ub[i]; val++) {
            int[] newNode = new int[node.length + 1];
            System.arraycopy(node, 0, newNode, 0, node.length);
            newNode[node.length] = val;
            combineRecursive(newNode, i + 1, lb, ub, leafs);
        }
    }

    /* Contains */
    public static boolean contains(int[] a, int i) {
        for (int j : a) {
            if (j == i) return true;
        }
        return false;
    }

    /** No tolarance for camparison. */
    public static int indexOf(double[] a, double b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == b) {
                return i;
            }
        }
        return -1;
    }
}
