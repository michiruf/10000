package com.github.tenthousand.bots.michiruf;

import java.util.function.BiConsumer;

/**
 * @author Michael Ruf
 * @since 2018-02-21
 */
class DiceStateChange {

    public static void calculateStateChanges(DiceState state) {
        for (int i = 1; i <= state.getRemainingDices(); i++) {
            calculatePossibleStateChanges(state, i);
        }
    }

    private static void calculatePossibleStateChanges(DiceState previousState, int numberOfDices) {
        DiceState nextState = DiceState.getForRemainingDices(previousState.getRemainingDices() - numberOfDices);
        BiConsumer<Integer, Float> add = (points, probability) ->
                previousState.addStateChange(new DiceStateChange(numberOfDices, points, probability, nextState));

        switch (numberOfDices) {
            case 1:
                // TODO Probs...
                add.accept(50, 0.0f);  // 1 * 5er
                add.accept(100, 0.0f); // 1 * 1er
                break;
            case 2:
                add.accept(100, 0.0f); // 2 * 5er
                add.accept(150, 0.0f); // 1 * 5er + 1 * 1er
                add.accept(200, 0.0f); // 2 * 1er
                break;
            case 3:
                // TODO
                // 1 x 1er, 2 x 5er
                // 2 x 1er, 1 x 5er
                // 1000-3er
                // 200-3er
                // 300-3er
                // 400-3er
                // 500-3er
                // 600-3er
            case 4:
                // TODO ...
                // This will get pretty complex
        }
    }

    private int dicesUsed;
    private int pointsGot;
    private float probability;
    private DiceState nextState;

    private DiceStateChange(int dicesUsed, int pointsGot, float probability, DiceState nextState) {
        this.dicesUsed = dicesUsed;
        this.pointsGot = pointsGot;
        this.probability = probability;
        this.nextState = nextState;
    }
}
