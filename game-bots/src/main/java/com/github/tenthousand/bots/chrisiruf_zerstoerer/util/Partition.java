package com.github.tenthousand.bots.chrisiruf_zerstoerer.util;

import java.util.ArrayList;

public class Partition {

    /**
     * 
     * @param number
     *            the number to be partitioned.
     * @param bins
     *            the maximum number of bins.
     * @return number partitions.
     */
    public static int[][] partitionOrdered(int number, int bins) {
        ArrayList<int[]> results = new ArrayList<int[]>();
        partition(number, new int[bins], 0, bins, results);
        return results.toArray(new int[results.size()][]);
    }

    private static void partition(int n, int[] a, int bin, int bins, ArrayList<int[]> results) {
        // return
        if (n == 0) {
            results.add(a);
            return;
        }
        if (bin >= bins) return;

        // branch
        for (int i = 0; i <= n; i++) {
            a[bin] = i;
            partition(n - i, AH.clone(a), bin + 1, bins, results);
        }
    }

    /**
     * 
     * @param number
     *            the number to be partitioned.
     * @param bins
     *            the maximum number of bins.
     * @param lb
     *            min number in a bin
     * @param ub
     *            max number in a bin
     * @return number partitions.
     */
    public static int[][] partitionOrdered(int number, int bins, int lb, int ub) {
        if (ub > number) throw new Error();
        if (lb < 0) throw new Error();
        ArrayList<int[]> results = new ArrayList<int[]>();
        partition(number, new int[bins], 0, bins, lb, ub, results);
        return results.toArray(new int[results.size()][]);
    }

    private static void partition(int n, int[] a, int bin, int bins, int lb, int ub,
            ArrayList<int[]> results) {
        if (bin == bins) {
            if (n == 0) results.add(a);
            return;
        }

        // branch
        for (int i = lb; i <= ub; i++) {
            a[bin] = i;
            partition(n - i, AH.clone(a), bin + 1, bins, lb, ub, results);
        }
    }
}
