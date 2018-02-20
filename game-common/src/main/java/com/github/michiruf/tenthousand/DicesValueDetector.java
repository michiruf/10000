package com.github.michiruf.tenthousand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Ruf
 * @since 2017-12-27
 */
public class DicesValueDetector {

    public static boolean hasAValue(Dice[] dices) {
        return new DicesValueDetector(dices).hasAValue();
    }

    public static boolean hasOnlyValues(Dice[] dices) {
        return new DicesValueDetector(dices).hasOnlyValues();
    }

    public static Dice[] getValuableDices(Dice[] dices) {
        return new DicesValueDetector(dices).getValuableDices();
    }

    public static boolean contains(Dice[] haystack, Dice[] needle) {
        return new DicesValueDetector(haystack).contains(needle);
    }

    public static int calculatePoints(Dice[] dices) {
        return new DicesValueDetector(dices).calculatePoints();
    }

    private Map<Dice, Integer> diceCountMap;
    private Map<Dice, Integer> diceCountMapFilterValuable;
    private Map<Dice, Integer> diceCountMapFilterNonValuable;

    public DicesValueDetector(Dice[] dices) {
        diceCountMap = new HashMap<>();
        fillMap(dices);
    }

    private void fillMap(Dice[] dices) {
        for (Dice dice : dices) {
            diceCountMap.put(dice, diceCountMap.getOrDefault(dice, 0) + 1);
        }
    }

    public Map<Dice, Integer> getDiceCountMap() {
        return diceCountMap;
    }

    public int getDiceCount(Dice dice) {
        return diceCountMap.getOrDefault(dice, 0);
    }

    public Map<Dice, Integer> filterValuableDices() {
        if (diceCountMapFilterValuable == null) {
            filterValuableDicesInternal();
        }
        // Return a copy to protect against manipulation
        return new HashMap<>(diceCountMapFilterValuable);
    }

    private void filterValuableDicesInternal() {
        diceCountMapFilterValuable = new HashMap<>();

        // Check for direct valuable dices
        for (Dice dice : Configuration.DICE_VALUES.keySet()) {
            int count = diceCountMap.getOrDefault(dice, 0);
            if (count > 0) {
                diceCountMapFilterValuable.put(dice, count);
            }
        }

        // Check for tripe values
        for (Dice dice : Configuration.DICE_TRIPLE_VALUES.keySet()) {
            int count = diceCountMap.getOrDefault(dice, 0);
            if (count >= 3) { // NOTE Hardcoded
                diceCountMapFilterValuable.put(dice, count);
            }
        }
    }

    public Dice[] getValuableDices() {
        List<Dice> result = new ArrayList<>();
        filterValuableDices().forEach((dice, count) -> {
            for (int i = 0; i < count; i++) {
                result.add(dice);
            }
        });
        return result.toArray(new Dice[result.size()]);
    }

    public Map<Dice, Integer> filterNonValuableDices() {
        if (diceCountMapFilterNonValuable == null) {
            filterNonValuableDicesInternal();
        }
        // Return a copy to protect against manipulation
        return new HashMap<>(diceCountMapFilterNonValuable);
    }

    private void filterNonValuableDicesInternal() {
        diceCountMapFilterNonValuable = new HashMap<>(diceCountMap);
        filterValuableDices().forEach((dice, integer) -> diceCountMapFilterNonValuable.remove(dice));
    }

    public Dice[] getNonValuableDices() {
        List<Dice> result = new ArrayList<>();
        filterNonValuableDices().forEach((dice, count) -> {
            for (int i = 0; i < count; i++) {
                result.add(dice);
            }
        });
        return result.toArray(new Dice[result.size()]);
    }

    public boolean hasAValue() {
        Map<Dice, Integer> values = filterValuableDices();
        return values.size() > 0;
    }

    public boolean hasOnlyValues() {
        Map<Dice, Integer> nonValues = filterNonValuableDices();
        return nonValues.size() == 0;
    }

    public boolean contains(Dice[] needle) {
        DicesValueDetector needleDetector = new DicesValueDetector(needle);
        for (Map.Entry<Dice, Integer> entry : needleDetector.diceCountMap.entrySet()) {
            int haystackDiceCount = diceCountMap.getOrDefault(entry.getKey(), 0);
            if (entry.getValue() > haystackDiceCount) {
                return false;
            }
        }
        return true;
    }

    public int calculatePoints() {
        Map<Dice, Integer> tmpMap = new HashMap<>(diceCountMap);
        int points = 0;

        // Calculate points for triples
        for (Map.Entry<Dice, Integer> entry : Configuration.DICE_TRIPLE_VALUES.entrySet()) {
            int count = tmpMap.getOrDefault(entry.getKey(), 0);
            // If enough dices for this type, calculate the points and remove the count (to avoid duplicate count)
            if (count >= 3) {
                int exp = count - 3; // NOTE Hardcoded
                points += entry.getValue() * Math.pow(2, exp); // NOTE Hardcoded
                tmpMap.remove(entry.getKey());
            }
        }

        // Calculate points for single values
        for (Map.Entry<Dice, Integer> entry : Configuration.DICE_VALUES.entrySet()) {
            int count = tmpMap.getOrDefault(entry.getKey(), 0);
            points += count * entry.getValue();
        }

        return points;
    }
}
