package com.github.michiruf.tenthousand;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Ruf
 * @since 2017-12-27
 */
public class DicesValueDetector {

    public static boolean hasAValue(Dice[] dices) {
        return internalHasAValue(new DicesValueDetector(dices));
    }

    public static boolean hasOnlyValues(Dice[] dices) {
        DicesValueDetector d = new DicesValueDetector(dices);
        internalHasAValue(d);
        return d.diceCountMap.size() == 0;
    }

    private static boolean internalHasAValue(DicesValueDetector d) {
        boolean hasValue = false;

        // Check for direct valuable dices
        for (Dice dice : Configuration.DICE_VALUES.keySet()) {
            hasValue |= d.diceCountMap.getOrDefault(dice, 0) > 0;
            d.diceCountMap.remove(dice);
        }

        // Check for tripe values
        for (Dice dice : Configuration.DICE_TRIPLE_VALUES.keySet()) {
            if (d.diceCountMap.getOrDefault(dice, 0) < 3) { // NOTE Hardcoded
                continue;
            }
            d.diceCountMap.remove(dice);
            hasValue = true;
        }

        return hasValue;
    }

    public static boolean contains(Dice[] haystack, Dice[] needle) {
        DicesValueDetector h = new DicesValueDetector(haystack);
        DicesValueDetector n = new DicesValueDetector(needle);
        for (Map.Entry<Dice, Integer> entry : n.diceCountMap.entrySet()) {
            int hC = h.diceCountMap.getOrDefault(entry.getKey(), 0);
            if (entry.getValue() > hC) {
                return false;
            }
        }
        return true;
    }

    public static int calculatePoints(Dice[] dices) {
        DicesValueDetector d = new DicesValueDetector(dices);
        int points = 0;

        // Calculate points for triples
        for (Map.Entry<Dice, Integer> entry : Configuration.DICE_TRIPLE_VALUES.entrySet()) {
            int count = d.diceCountMap.getOrDefault(entry.getKey(), 0);
            // If enough dices for this type, calculate the points and remove the count (to avoid duplicate count)
            if (count >= 3) {
                int exp = count - 3; // NOTE Hardcoded
                points += entry.getValue() * Math.pow(2, exp); // NOTE Hardcoded
                d.diceCountMap.remove(entry.getKey());
            }
        }

        for (Map.Entry<Dice, Integer> entry : Configuration.DICE_VALUES.entrySet()) {
            int count = d.diceCountMap.getOrDefault(entry.getKey(), 0);
            points += count * entry.getValue();
        }

        return points;
    }

    private Map<Dice, Integer> diceCountMap;

    private DicesValueDetector(Dice[] dices) {
        diceCountMap = new HashMap<>();
        for (Dice dice : dices) {
            addOneCount(dice);
        }
    }

    private void addOneCount(Dice dice) {
        diceCountMap.put(dice, diceCountMap.getOrDefault(dice, 0) + 1);
    }
}
