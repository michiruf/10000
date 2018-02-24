package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.*;
import com.github.michiruf.tenthousand.exception.GameException;

/**
 * @author Michael Ruf
 * @since 2018-02-18
 */
class MichiRufDecisions implements PlayerDecisionInterface {

    private Player me;

    @Override
    public void onGameStart(Player[] players, Player self) {
        me = self;
    }

    @Override
    public AdoptAction onTurnStart(RoundAdoptionState roundAdoptionState) {
        Logger.log();
        Logger.log("///////// ROUND START //////////");
        Logger.log("Points: " + me.getPoints());
        Logger.log("Adoption: no");
        Logger.log("////////////////////////////////");
        // For now, because other persons may have always correct decisions, we do never want to adopt stuff
        return AdoptAction.IGNORE;
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
        DiceAction decision = new TurnDecider(newDices, pointsThisRoundSoFar).calculateDecision();
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
