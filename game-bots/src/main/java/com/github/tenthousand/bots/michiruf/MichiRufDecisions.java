package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.AdoptAction;
import com.github.michiruf.tenthousand.Dice;
import com.github.michiruf.tenthousand.DiceAction;
import com.github.michiruf.tenthousand.DicesValueDetector;
import com.github.michiruf.tenthousand.Player;
import com.github.michiruf.tenthousand.PlayerDecisionInterface;
import com.github.michiruf.tenthousand.RoundAdoptionState;
import com.github.michiruf.tenthousand.exception.GameException;

/**
 * @author Michael Ruf
 * @since 2018-02-18
 */
class MichiRufDecisions implements PlayerDecisionInterface {

    private Player me;
    private int adoptedPointsThisRound;

    @Override
    public void onGameStart(Player[] players, Player self) {
        me = self;
    }

    @Override
    public AdoptAction onTurnStart(RoundAdoptionState roundAdoptionState) {
        // Reset the adopted points on turn start
        adoptedPointsThisRound = 0;

        Logger.log();
        Logger.log("///////// ROUND START //////////");
        Logger.log("Points: " + me.getPoints());
        Logger.logNoNL("Adoption available: ");
        if (roundAdoptionState.isAdoptionAvailable()) {
            Logger.log(String.format("%d dices left, %d points",
                    roundAdoptionState.adoptedNumberOfDicesRemaining,
                    roundAdoptionState.adoptedPoints));
        } else {
            Logger.log("no");
        }
        AdoptAction decision = new AdoptionDecider(roundAdoptionState, me.getPoints()).calculateDecision();
        Logger.logNoNL("Adoption: ");
        if (decision == AdoptAction.ADOPT) {
            adoptedPointsThisRound = roundAdoptionState.adoptedPoints;
            Logger.log("yes");
        } else {
            Logger.log("no");
        }
        Logger.log("////////////////////////////////");
        return decision;
    }

    @Override
    public DiceAction onTurnDiceRolled(Dice[] newDices, int pointsThisRoundSoFar) {
        // Enter the game and continue rolling if there are 4 or more dices left
        // NOTE Could be improved by not always taking all valuable dices
        // TODO Use Strategy pattern
        if (!me.hasEnteredGame()) {
            DicesValueDetector d = new DicesValueDetector(newDices);
            Dice[] valuableDices = d.getValuableDices();
            boolean continueTilPointsReached = pointsThisRoundSoFar + d.calculatePoints() < 1000;
            boolean continueIfTooMuchDicesLeft = valuableDices.length <= 2;
            return new DiceAction(valuableDices, continueTilPointsReached || continueIfTooMuchDicesLeft);
        }

        Logger.log("Points this turn (before decision): " + pointsThisRoundSoFar);
        Logger.logDices("Dices: {%s}", newDices);
        DiceAction decision = new TurnDecider(newDices, pointsThisRoundSoFar, adoptedPointsThisRound)
                .calculateDecision();
        Logger.log("================================");
        return decision;
    }

    @Override
    public void onTurnEnd(boolean successfulRound, int pointsReceived) {
        Logger.log("\\\\\\\\\\\\\\\\\\\\ ROUND END \\\\\\\\\\\\\\\\\\\\\\");
        Logger.log("Round state: " + (successfulRound ? "success" : "failure"));
        Logger.log("Points got: " + pointsReceived);
        Logger.log("Points: " + me.getPoints());
        Logger.log("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
    }

    @Override
    public void onGameEnd(Player[] players, Player[] wonPlayers) {
        // No need to do something here yet
    }

    @Override
    public void onError(GameException e) {
        e.printStackTrace();
    }
}
