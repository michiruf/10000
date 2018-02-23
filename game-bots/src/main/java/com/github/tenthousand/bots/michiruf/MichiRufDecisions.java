package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.*;
import com.github.michiruf.tenthousand.exception.GameException;

/**
 * @author Michael Ruf
 * @since 2018-02-18
 */
class MichiRufDecisions implements PlayerDecisionInterface {

    public static Player[] playersStatic; // TODO Just for debugging static, remove!
    private Player[] players;
    private Player me;

    @Override
    public void onGameStart(Player[] players, Player self) {
        playersStatic = players;
        this.players = players;
        me = self;
    }

    @Override
    public AdoptAction onTurnStart(RoundAdoptionState roundAdoptionState) {
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

        return new TurnDecider(newDices, pointsThisRoundSoFar).calculateDecision();
    }

    @Override
    public void onTurnEnd(boolean successfulRound, int pointsReceived) {

    }

    @Override
    public void onGameEnd(Player[] players, Player[] wonPlayers) {

    }

    @Override
    public void onError(GameException e) {
        e.printStackTrace();
    }
}
