package com.github.tenthousand.bots.michiruf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Ruf
 * @since 2018-02-21
 */
class DiceState {

    private static List<DiceState> states;

    public static void initialize(int numberOfDices) {
        // Create the states
        states = new ArrayList<>();
        for (int i = 1; i <= numberOfDices; i++) {
            DiceState state = new DiceState(i);
            states.add(state);
        }

        // Calculate state changes for these states
        DiceState rootState = getForRemainingDices(numberOfDices);
        for (int i = 1; i <= numberOfDices; i++) {
            DiceStateChange.calculateStateChanges(getForRemainingDices(i), rootState);
        }

//        // Calculate the expected profit cache
//        for (int i = 1; i <= numberOfDices; i++) {
//            DiceState s = getForRemainingDices(i);
//            s.expectedProfit = s.calculateExpectedProfitInternal(1.0);
//        }
        getForRemainingDices(6).expectedProfit = 653.2731084149784;
        getForRemainingDices(5).expectedProfit = 496.2245675871217;
        getForRemainingDices(4).expectedProfit = 367.3389475898621;
        getForRemainingDices(3).expectedProfit = 274.69541320129343;
        getForRemainingDices(2).expectedProfit = 227.67216492171582;
        getForRemainingDices(1).expectedProfit = 241.2684345340645;


//        // TODO Remove test
//        int remaining = 2;
//        double p = DiceState.getForRemainingDices(remaining).testSumProb();
//        double pn = DiceProbability.calculateFailingProbability(remaining);
//        System.err.println(p);
//        System.err.println(pn);
//        System.err.println(p + pn);
//        System.exit(100);
//        // Works -> NICE
    }

    public static DiceState getForRemainingDices(int remainingDices) {
        DiceState result = states.stream()
                .filter(diceState -> diceState.remainingDices == remainingDices)
                .findFirst()
                .orElse(null);
        if (result == null) {
            throw new IllegalArgumentException("Dice state for remaining-dices-count (" + remainingDices + ") " +
                    "does not exist!");
        }
        return result;
    }

    private final int remainingDices;
    private final List<DiceStateChange> stateChanges;

    private double expectedProfit = 0;

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

    public double calculateExpectedProfit(double pointsSoFarThisTurn, double lossProtectionFactor) {
        if (expectedProfit == 0) {
            throw new IllegalStateException("Expected profits were not calculated yet!");
        }
        double failProbability = DiceProbability.calculateFailingProbability(remainingDices);
        double win = expectedProfit;
        double loss = pointsSoFarThisTurn * failProbability;
        return win - loss * lossProtectionFactor;
    }

    private double calculateExpectedProfitInternal(double previousProbabilities) {
        double weightedProfitSum = 0.0;
        for (DiceStateChange stateChange : stateChanges) {
            // Calculate the profit
            double profit = stateChange.pointsGot;
            double probability = previousProbabilities * stateChange.probability;
            double weightedProfit = probability * profit;

            // Calculate the next states profit if this is still worth enough
            if (weightedProfit > 0.0000001) {
                weightedProfit += stateChange.nextState
                        .calculateExpectedProfitInternal(probability);
            }

            weightedProfitSum += weightedProfit;
        }
        return weightedProfitSum;
    }

    private double testSumProb() {
        double probSum = 0.0;
        for (DiceStateChange stateChange : stateChanges) {
            probSum += stateChange.probability;
        }
        return probSum;
    }

    @Override
    public String toString() {
        return "DiceState{" +
                "remainingDices=" + remainingDices + ", " +
                "stateChanges[" + stateChanges.size() + "]" +
                "}";
    }
}
