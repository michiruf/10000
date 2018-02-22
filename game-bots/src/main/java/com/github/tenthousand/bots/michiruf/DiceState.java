package com.github.tenthousand.bots.michiruf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Ruf
 * @since 2018-02-21
 */
class DiceState {

    // NOTE Could be just a list!
    private static Map<Integer, DiceState> states;

    public static void initialize(int numberOfDices) {
        states = new HashMap<>();
        // Initialize the states
        for (int i = 1; i <= numberOfDices; i++) {
            DiceState state = new DiceState(i);
            states.put(i, state);
        }
        // Calculate state changes for these states
        for (int i = 1; i <= numberOfDices; i++) {
            DiceStateChange.calculateStateChanges(getForRemainingDices(i));
        }
    }

    public static DiceState getForRemainingDices(int remainingDices) {
        DiceState result = states.get(remainingDices);
        if (result == null) {
            throw new IllegalArgumentException("Dice state for remaining-dices-count (" + remainingDices + ") " +
                    "does not exist!");
        }
        return result;
    }

    private final int remainingDices;
    private final List<DiceStateChange> stateChanges;

    private DiceState(int remainingDices) {
        this.remainingDices = remainingDices;
        stateChanges = new ArrayList<>();
    }

    public int getRemainingDices() {
        return remainingDices;
    }

    public void addStateChange(DiceStateChange stateChange) {
        stateChanges.add(stateChange);
    }

    public double calculateExpectedProfit(double pointsSoFarThisTurn) {
        double failProbability = ProbabilityCalculator.calculateFailingProbability(remainingDices);
        double win = calculateExpectedProfitInternal(pointsSoFarThisTurn);
        double loss = pointsSoFarThisTurn * failProbability;
        return win - loss;
    }

    private double calculateExpectedProfitInternal(double pointsSoFarThisTurn) {
        double profitSum = 0.0;
        for (DiceStateChange stateChange : stateChanges) {
            // Calculate the profit
            double profit = stateChange.probability * stateChange.pointsGot;
            double profitIncludingPointsSoFar = pointsSoFarThisTurn + profit;

            // Add the profit of the next state if existent
            if (stateChange.nextState != null) {
                profit += stateChange.nextState.calculateExpectedProfitInternal(profitIncludingPointsSoFar);
            }
            profitSum += profit;
        }
        return profitSum;
    }

    @Override
    public String toString() {
        return "DiceState{" +
                "remainingDices=" + remainingDices + ", " +
                "stateChanges[" + stateChanges.size() + "]" +
                "}";
    }
}
