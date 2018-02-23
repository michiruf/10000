package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.Configuration;
import com.github.michiruf.tenthousand.Dice;
import com.github.michiruf.tenthousand.DicesValueDetector;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Michael Ruf
 * @since 2018-02-21
 */
class DiceStateChange {

    public static void calculateStateChanges(DiceState state, int usedDices, DiceState rootState) {
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
            int remainingDices = state.getRemainingDices() - dices.length;
            state.addStateChange(new DiceStateChange(
                    new DicesValueDetector(dices).calculatePoints(),
                    (double) entry.getValue() / (double) diceCombinations.size(),
                    state,
                    remainingDices != 0 ? DiceState.getForRemainingDices(remainingDices) : rootState
            ));
        }
    }

    public final int pointsGot;
    public final double probability;
    public final DiceState previousState;
    public final DiceState nextState;

    private DiceStateChange(int pointsGot, double probability, DiceState previousState, DiceState nextState) {
        this.pointsGot = pointsGot;
        this.probability = probability;
        this.previousState = previousState;
        this.nextState = nextState;
    }

    public int getDicesUsed() {
        return previousState.getRemainingDices() - nextState.getRemainingDices() % Configuration.NO_DICES;
    }

    @Override
    public String toString() {
        return "DiceStateChange{" +
                "dicesUsed=" + getDicesUsed() + ", " +
                "pointsGot=" + pointsGot + ", " +
                "probability=" + probability + ", " +
                "nextState=" + nextState.getRemainingDices() + "-dices" +
                "}";
    }
}
