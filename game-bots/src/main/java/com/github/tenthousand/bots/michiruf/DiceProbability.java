package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.Dice;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Michael Ruf
 * @since 2018-02-23
 */
class DiceProbability {

    public static List<Dice[]> calculateDiceCombinationsForDiceCount(int numberOfDices) {
        List<Dice[]> diceCombinations = new ArrayList<>();
        for (int i = 0; i < Math.pow(6, numberOfDices); i++) {
            Dice[] diceCombination = new Dice[numberOfDices];
            for (int j = 0; j < numberOfDices; j++) {
                diceCombination[j] = Dice.fromSpot(i / (int) Math.pow(6, j) % 6 + 1);
            }
            diceCombinations.add(diceCombination);
        }
        return diceCombinations;
    }

    public static Map<Dice[], Integer> reduceCombinationsToUniqueAndCount(List<Dice[]> diceCombinations) {
        Map<List<Dice>, Integer> uniqueDiceCombinationCounts = new HashMap<>();
        diceCombinations.forEach(diceCombination -> {
            // Use a list first, because it has a proper hashCode, which an array has not
            List<Dice> dices = Arrays.stream(diceCombination)
                    .sorted(Comparator.comparingInt(Dice::getValue))
                    .collect(Collectors.toList());
            uniqueDiceCombinationCounts.put(dices, uniqueDiceCombinationCounts.getOrDefault(dices, 0) + 1);
        });
        Map<Dice[], Integer> result = new HashMap<>();
        uniqueDiceCombinationCounts.forEach((dices, integer) -> {
            result.put(dices.toArray(new Dice[dices.size()]), integer);
        });
        return result;
    }

    public static double calculateFailingProbability(int numberOfDices) {
        // Note, that these numbers came from the test where all possibilities were gone through
        // TODO Calculate this data dice dependent!
        switch (numberOfDices) {
            case 1:
                // 4 / 6
                return 2.0 / 3.0;
            case 2:
                // 16 / 36
                return 16.0 / 36.0;
            case 3:
                // 60 / 216
                return 5.0 / 18.0;
            case 4:
                // 204 / 1296
                return 17.0 / 108.0;
            case 5:
                // 600 / 7776
                return 25.0 / 324.0;
            case 6:
                // 1440 / 46656
                return 1440.0 / 46656.0;
        }
        // Default should be 1 because this probability decreased the more dices there are
        return 1;
    }

    private static final double _1 = 1.0 / 6.0;
    private static final double _2 = 2 * _1;
    private static final double _3 = 3 * _1;
    private static final double _4 = 4 * _1;
    private static final double _5 = 5 * _1;

    // NOTE This is not correct yet...
    public static double calculateFailingProbability2(int numberOfDices) {
        switch (numberOfDices) {
            case 1:
            case 2:
                return Math.pow(_4, numberOfDices);
            case 3:
            case 4:
                return Math.pow(_4, 2) * Math.pow(_3, numberOfDices % 2 + 1);
            case 5:
            case 6:
                return Math.pow(_4, 2) * Math.pow(_3, 2) * Math.pow(_2, numberOfDices % 2 + 1);
        }
        // Default should be 1 because this probability decreased the more dices there are
        return 1;
    }
}
