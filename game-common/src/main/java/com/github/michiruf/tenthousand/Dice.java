package com.github.michiruf.tenthousand;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Michael Ruf
 * @since 2017-12-26
 */
public class Dice {

    public static final Dice ONE = new Dice(1);
    public static final Dice TWO = new Dice(2);
    public static final Dice THREE = new Dice(3);
    public static final Dice FOUR = new Dice(4);
    public static final Dice FIVE = new Dice(5);
    public static final Dice SIX = new Dice(6);

    private static final Random random = new Random();

    public static Dice random() {
        return fromSpot(random.nextInt(Configuration.DICE_PIPS) + 1);
    }

    public static Dice[] random(int count) {
        Dice[] result = new Dice[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = random();
        }
        return result;
    }

    public static Dice[] randomSorted(int count) {
        return Arrays.asList(random(count)).stream()
                .sorted((o1, o2) -> o1.getValue() - o2.getValue())
                .toArray(Dice[]::new);
    }

    public static Dice fromSpot(int spot) {
        switch (spot) {
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
            case 6:
                return SIX;
            default:
                if (spot > Configuration.DICE_PIPS) {
                    throw new IllegalArgumentException("Spot does not exist");
                }
                return new Dice(spot);
        }
    }

    private int value;

    // NOTE Was private
    public Dice(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dice dice = (Dice) o;
        return value == dice.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
