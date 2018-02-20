package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.*;
import com.github.michiruf.tenthousand.exception.GameException;

/**
 * @author Michael Ruf
 * @since 2018-02-18
 */
class MichiRufDecisions implements PlayerDecisionInterface {

    private Player[] players;
    private Player me;

    @Override
    public void onGameStart(Player[] players, Player self) {
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
        // For now, just enter the game by continuing until there are 1000 points
        if (!me.hasEnteredGame()) {
            DicesValueDetector d = new DicesValueDetector(newDices);
            return new DiceAction(d.getValuableDices(), pointsThisRoundSoFar + d.calculatePoints() < 1000);
        }

        int dp = DicesValueDetector.calculatePoints(newDices);
        int sp = dp + pointsThisRoundSoFar;
        System.out.println("Points " + pointsThisRoundSoFar + " + " + dp + " = " + sp);
        return new DiceAction(DicesValueDetector.getValuableDices(newDices), sp < 250);
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
