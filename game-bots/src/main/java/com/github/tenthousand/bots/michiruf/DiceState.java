package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.Dice;
import com.github.michiruf.tenthousand.DicesValueDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Michael Ruf
 * @since 2018-02-21
 */
class DiceState {

    // NOTE Could be just a list!
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
            DiceState current = getForRemainingDices(i);
            for (int j = 1; j <= current.getRemainingDices(); j++) {
                calculateStateChanges(current, j, rootState);
            }
        }
    }

    private static void calculateStateChanges(DiceState state, int usedDices, DiceState rootState) {
        // Calculate the reduced dice combinations count
        List<Dice[]> diceCombinations = DiceProbability.calculateDiceCombinationsForDiceCount(usedDices);
        List<Dice[]> filteredDiceCombinations = diceCombinations.stream()
                .filter(dices -> new DicesValueDetector(dices).hasOnlyValues())
                .collect(Collectors.toList());
        Map<Dice[], Integer> reducedDiceCombinationCounts = DiceProbability.reduceCombinationsToUniqueAndCount(
                filteredDiceCombinations);

        // Create the dice state changes with this additional information
        for (Map.Entry<Dice[], Integer> entry : reducedDiceCombinationCounts.entrySet()) {
            Dice[] dices = entry.getKey();
            int remainingDices = usedDices - dices.length;
            state.addStateChange(new DiceStateChange(
                    dices.length,
                    new DicesValueDetector(dices).calculatePoints(),
                    (double) entry.getValue() / (double) diceCombinations.size(),
                    remainingDices != 0 ? DiceState.getForRemainingDices(remainingDices) : rootState
            ));
        }
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

    public double calculateProbSum() {
        double prob = 0.0;
        for (DiceStateChange stateChange : stateChanges) {
            prob += stateChange.probability;
            if (stateChange.nextState != null) {
                prob += stateChange.probability * stateChange.nextState.calculateProbSum();
            }
        }
        return prob;
    }

    public double calculateExpectedProfit(double pointsSoFarThisTurn) {
        double failProbability = ProbabilityCalculator.calculateFailingProbability(remainingDices);
        double win = calculateExpectedProfitInternal(1.0);
        double loss = pointsSoFarThisTurn * failProbability;
        return win - loss;
    }

    private double calculateExpectedProfitInternal(double previousProbabilities) {
        double weightedProfitSum = 0.0;
        for (DiceStateChange stateChange : stateChanges) {
            // Calculate the profit
            double profit = stateChange.pointsGot;
            double probability = previousProbabilities * stateChange.probability;
            double weightedProfit = probability * profit;

            // Calculate the next states profit if this is still worth enough
            if (weightedProfit > 1.0) {
                weightedProfit += stateChange.nextState
                        .calculateExpectedProfitInternal(probability);
            }

            weightedProfitSum += weightedProfit;
        }
        return weightedProfitSum;
    }

    @Override
    public String toString() {
        return "DiceState{" +
                "remainingDices=" + remainingDices + ", " +
                "stateChanges[" + stateChanges.size() + "]" +
                "}";
    }
}
