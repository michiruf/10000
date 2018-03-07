package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.Dice;
import com.github.michiruf.tenthousand.DiceAction;
import com.github.michiruf.tenthousand.DicesValueDetector;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Michael Ruf
 * @since 2018-02-21
 */
class TurnDecider {

    // I do not know why this is a good value...
    // This factor is multiplied by the loss in the expected profit function
    // to reduce the bots risk of loosing all points in a round
    private static final double lossProtectionFactor = 2.85;

    private final int pointsThisRoundSoFar;
    private final int adoptedPointsThisRound;
    private final DicesValueDetector valueDetector;

    public TurnDecider(Dice[] dices, int pointsThisRoundSoFar, int adoptedPointsThisRound) {
        this.pointsThisRoundSoFar = pointsThisRoundSoFar;
        this.adoptedPointsThisRound = adoptedPointsThisRound;
        valueDetector = new DicesValueDetector(dices);
    }

    public DiceAction calculateDecision() {
        Dice[] valueDices = valueDetector.getValuableDices();
        int valuePoints = valueDetector.calculatePoints();
        int nonValueDicesCount = valueDetector.getNonValuableDices().length;

        // If there a no non value dices, just take all and continue with all dices!
        if (nonValueDicesCount == 0) {
            Logger.log("Took all dices!");
            return new DiceAction(valueDices, true);
        }

        // Get the subsets
        List<List<Dice>> subsetsAll = MyMath.findSubsets(Arrays.asList(valueDices));
        List<List<Dice>> subsetsWithoutEmpty = MyMath.subsetExcludeEmpty(subsetsAll);
        List<List<Dice>> subsetsWithoutDuplicates = MyMath.subsetExcludeDuplicateEntries(subsetsWithoutEmpty);
        List<Dice[]> subsets = subsetsWithoutDuplicates.stream()
                .map(subset -> subset.toArray(new Dice[subset.size()]))
                .filter(subset -> new DicesValueDetector(subset).hasOnlyValues())
                .sorted(Comparator.comparingInt(o -> o.length))
                .collect(Collectors.toList());
        Logger.logNoNL("Subsets: ");
        subsets.forEach(dices -> Logger.logDicesNoNL("{%s} ", dices));
        Logger.log();

        // Filter subsets and find the one with the most profit
        double maxProfit = valuePoints;
        int maxProfitSubsetIndex = -1;
        for (int i = 0; i < subsets.size(); i++) {
            Dice[] subset = subsets.get(i);
            int subsetValue = new DicesValueDetector(subset).calculatePoints();
            DiceState subsetState = DiceState.getForRemainingDices(nonValueDicesCount +
                    (valueDices.length - subset.length));
            double subsetProfit = subsetValue + subsetState.calculateExpectedProfit(
                    pointsThisRoundSoFar + adoptedPointsThisRound + valuePoints, lossProtectionFactor);
            if (subsetProfit > maxProfit) {
                maxProfit = subsetProfit;
                maxProfitSubsetIndex = i;
            }
        }

        // If a maximum profit was found (greater 0), continue!
        if (maxProfitSubsetIndex >= 0) {
            Dice[] subset = subsets.get(maxProfitSubsetIndex);
            Logger.logDices("Took subset: {%s}", subset);
            return new DiceAction(subset, true);
        }

        // Else, just keep the stuff if we have the round limit not reached yet
        Logger.logDices("Took valuable dices: {%s}", valueDices);
        return new DiceAction(valueDices,
                valuePoints + pointsThisRoundSoFar < 250);
    }
}
