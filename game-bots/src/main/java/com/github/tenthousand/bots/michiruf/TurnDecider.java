package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.Dice;
import com.github.michiruf.tenthousand.DiceAction;
import com.github.michiruf.tenthousand.DicesValueDetector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Michael Ruf
 * @since 2018-02-21
 */
class TurnDecider {

    private final Dice[] dices; // TODO At least for debugging
    private final int pointsThisRoundSoFar;
    private final DicesValueDetector valueDetector;

    public TurnDecider(Dice[] dices, int pointsThisRoundSoFar) {
        this.dices = dices;
        this.pointsThisRoundSoFar = pointsThisRoundSoFar;
        valueDetector = new DicesValueDetector(dices);
    }

    public DiceAction calculateDecision() {
        Dice[] valueDices = valueDetector.getValuableDices();
        int nonValueDicesCount = valueDetector.getNonValuableDices().length;

        // If there a no non value dices, just take all and continue with all dices!
        if (nonValueDicesCount == 0) {
            return new DiceAction(valueDices, true);
        }

        // Get the subsets, filter them and find the one with the most profit
        List<Dice[]> subsets = MyMath.findSubsetsExcludingEmpty(Arrays.asList(valueDices)).stream()
                .map(subset -> subset.toArray(new Dice[subset.size()]))
                .filter(subset -> new DicesValueDetector(subset).hasOnlyValues())
                .collect(Collectors.toList());
        double maxProfit = 0;
        int maxProfitSubsetIndex = -1;
        for (int i = 0; i < subsets.size(); i++) {
            Dice[] subset = subsets.get(i);
            int subsetValue = new DicesValueDetector(subset).calculatePoints();
            double subsetProfit = subsetValue + DiceState
                    .getForRemainingDices(nonValueDicesCount + (valueDices.length - subset.length))
                    .calculateExpectedProfit(pointsThisRoundSoFar + subsetValue);
            // TODO Change "calculateExpectedProfit", because its not good enough..
            if (subsetProfit > maxProfit) {
                maxProfit = subsetProfit;
                maxProfitSubsetIndex = i;
            }
        }

        // If a maximum profit was found (greater 0), continue!
        if (maxProfitSubsetIndex >= 0) {
            return new DiceAction(subsets.get(maxProfitSubsetIndex), true);
        }

        // Else, just keep the stuff if we have the round limit not reached yet
        return new DiceAction(valueDices,
                valueDetector.calculatePoints() + pointsThisRoundSoFar < 250);
    }

    // TODO LOGGING! to get whats wrong here

    // TODO Add a SubsetDataHolder to not create multiple DicesValueDetector's (done at filtering AND point calc)
}
