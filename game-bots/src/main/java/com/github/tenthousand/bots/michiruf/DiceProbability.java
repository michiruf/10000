package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.Dice;
import com.github.michiruf.tenthousand.DicesValueDetector;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Michael Ruf
 * @since 2018-02-23
 */
class DiceProbability {

    // TODO Calculate all possibilities of dices and calculate the next state probabilities and "worth's" for each

    public static void initialize(int numberOfDices) {
        DiceState rootState = DiceState.getForRemainingDices(4); // TODO Hardcoded

        for (int i = 1; i <= numberOfDices; i++) {
            // Calculate the reduced dice combinations count
            List<Dice[]> diceCombinations = calculateDiceCombinationsForDiceCount(i);
            List<Dice[]> filteredDiceCombinations = diceCombinations.stream()
                    .filter(dices -> new DicesValueDetector(dices).hasOnlyValues())
                    .collect(Collectors.toList());
            Map<Dice[], Integer> reducedDiceCombinationCounts = reduceCombinationsToUniqueAndCount(filteredDiceCombinations);

            // Create the dice state changes with this additional information
            DiceState state = DiceState.getForRemainingDices(i);
            for (Map.Entry<Dice[], Integer> entry : reducedDiceCombinationCounts.entrySet()) {
                Dice[] dices = entry.getKey();
                int remainingDices = i - dices.length;
                state.addStateChange(new DiceStateChange(
                        dices.length,
                        new DicesValueDetector(dices).calculatePoints(),
                        (double) entry.getValue() / (double) diceCombinations.size(),
                        remainingDices != 0 ? DiceState.getForRemainingDices(remainingDices) : rootState
                ));
            }
        }
    }

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

//    public static Map<Set<Dice>, Integer> reduceCombinationsToUniqueAndCount_OLD(List<Dice[]> diceCombinations) {
//        Map<Set<Dice>, Integer> uniqueDiceCombinationCounts = new HashMap<>();
//        diceCombinations.forEach(diceCombination -> {
//            DicesValueDetector d = new DicesValueDetector(diceCombination);
//            if (d.hasOnlyValues()) {
//                Set<Dice> dices = new HashSet<>();
//                dices.addAll(Arrays.asList(d.getValuableDices()));
//                uniqueDiceCombinationCounts.put(dices, uniqueDiceCombinationCounts.getOrDefault(dices, 0) + 1);
//            }
//        });
//        return uniqueDiceCombinationCounts;
//    }

    private static class DiceProbabilityEntry {

    }
}
