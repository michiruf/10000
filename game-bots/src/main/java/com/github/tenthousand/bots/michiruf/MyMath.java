package com.github.tenthousand.bots.michiruf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Michael Ruf
 * @since 2017-12-26
 */
class MyMath {

    public static long factorial(int n) {
        long result = 1;
        for (int factor = 2; factor <= n; factor++) {
            result *= factor;
        }
        return result;
    }

    /**
     * @see <a href="https://stackoverflow.com/a/4640179/693134">Stackoverflow post by Joo Silva</a>
     */
    public static <T> List<List<T>> findSubsets(List<T> originalList) {
        List<List<T>> sets = new ArrayList<>();
        if (originalList.isEmpty()) {
            sets.add(new ArrayList<>());
            return sets;
        }
        List<T> list = new ArrayList<>(originalList);
        T head = list.get(0);
        List<T> rest;
        rest = new ArrayList<>(list.subList(1, list.size()));
        for (List<T> set : findSubsets(rest)) {
            List<T> newList = new ArrayList<>();
            newList.add(head);
            newList.addAll(set);
            sets.add(newList);
            sets.add(set);
        }
        return sets;
    }

    public static <T> List<List<T>> subsetExcludeEmpty(List<List<T>> subsets) {
        return subsets.stream()
                .filter(ts -> ts.size() > 0)
                .collect(Collectors.toList());
    }

    public static <T> List<List<T>> subsetExcludeDuplicateEntries(List<List<T>> subsets) {
        return new ArrayList<>(new HashSet<>(subsets));
    }
}
