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

    private final DicesValueDetector valueDetector;
    private final int pointsThisRoundSoFar;

    public TurnDecider(Dice[] dices, int pointsThisRoundSoFar) {
        valueDetector = new DicesValueDetector(dices);
        this.pointsThisRoundSoFar = pointsThisRoundSoFar;
    }

    public DiceAction calculateDecision() {
        Dice[] valueDices = valueDetector.getValuableDices();
        int nonValueDicesCount = valueDetector.getNonValuableDices().length;

        // TODO Remove fake data
//        valueDices = new Dice[]{Dice.fromSpot(1), Dice.fromSpot(5)};
//        nonValueDicesCount = 1;
        // TODO It currently takes only the dice 5, which makes no sense!
        // -> DiceState.getForRemainingDices(1).calculateExpectedProfit(50)   ==> -8.3
        // -> DiceState.getForRemainingDices(1).calculateExpectedProfit(100)  ==> -41.6
        // TODO WTF -> Error in calculateExpectedProfit...!

        // If there a no non value dices, just take all and continue with 6 dices!
        if (nonValueDicesCount == 0) {
            return new DiceAction(valueDices, true);
        }

        // Get the subsets, filter them and find the one with the most profit
        List<Dice[]> subsets = MyMath.findSubsetsExcludingEmpty(Arrays.asList(valueDices)).stream()
                .map(subset -> subset.toArray(new Dice[subset.size()]))
                .filter(subset -> new DicesValueDetector(subset).hasOnlyValues())
                .collect(Collectors.toList());
        double maxProfit = 0;
        int maxProfitIndex = -1;
        for (int i = 0; i < subsets.size(); i++) {
            Dice[] subset = subsets.get(i);
            int subsetValue = new DicesValueDetector(subset).calculatePoints();
            double subsetProfit = DiceState
                    .getForRemainingDices(nonValueDicesCount + (valueDices.length - subset.length))
                    .calculateExpectedProfit(pointsThisRoundSoFar + subsetValue);
            if (subsetProfit > maxProfit) {
                maxProfit = subsetProfit;
                maxProfitIndex = i;
            }
        }

        // If a maximum profit was found (greater 0), continue!
        if (maxProfitIndex >= 0) {
            return new DiceAction(subsets.get(maxProfitIndex), true);
        }

        // Else, just keep the stuff if we have the round limit reached yet
        return new DiceAction(valueDices,
                valueDetector.calculatePoints() + pointsThisRoundSoFar < 250);
    }

    // TODO Add a SubsetDataHolder to not create multiple DicesValueDetector's (done at filtering AND point calc)
}
