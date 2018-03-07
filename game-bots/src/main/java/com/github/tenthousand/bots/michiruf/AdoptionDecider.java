package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.AdoptAction;
import com.github.michiruf.tenthousand.Configuration;
import com.github.michiruf.tenthousand.RoundAdoptionState;

/**
 * @author Michael Ruf
 * @since 2018-02-21
 */
class AdoptionDecider {

    private final RoundAdoptionState roundAdoptionState;
    private final int myPoints;

    public AdoptionDecider(RoundAdoptionState roundAdoptionState, int myPoints) {
        this.roundAdoptionState = roundAdoptionState;
        this.myPoints = myPoints;
    }

    public AdoptAction calculateDecision() {
        // If there is no adoption, don't do it
        if (!roundAdoptionState.isAdoptionAvailable()) {
            return AdoptAction.IGNORE;
        }

        // If there are not enough points, don't do it
        if (roundAdoptionState.adoptedPoints > myPoints) {
            Logger.log("Cannot take adoption because too less points");
            return AdoptAction.IGNORE;
        }

        // Calculate the points one would take by normal rolling
        double averagePoints = DiceState.getForRemainingDices(Configuration.NO_DICES)
                .calculateExpectedProfit(0, 1.0);
        Logger.log(String.format("Average points for %d dices: %f", Configuration.NO_DICES, averagePoints));

        // Calculate the average points possible by taking those
        double pointsMayPossibleByAdopting = roundAdoptionState.adoptedPoints +
                DiceState.getForRemainingDices(roundAdoptionState.adoptedNumberOfDicesRemaining)
                        .calculateExpectedProfit(roundAdoptionState.adoptedPoints, 1.0);
        Logger.log(String.format("Points possible by adopting: %f", pointsMayPossibleByAdopting));

        if (pointsMayPossibleByAdopting > averagePoints) {
            return AdoptAction.ADOPT;
        }
        return AdoptAction.IGNORE;
    }
}
