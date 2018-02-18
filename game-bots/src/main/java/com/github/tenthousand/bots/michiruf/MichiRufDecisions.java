package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.*;
import com.github.michiruf.tenthousand.exception.GameException;

/**
 * @author Michael Ruf
 * @since 2018-02-18
 */
public class MichiRufDecisions implements PlayerDecisionInterface {

    @Override
    public void onGameStart(Player[] players, Player self) {

    }

    @Override
    public AdoptAction onTurnStart(RoundAdoptionState roundAdoptionState) {
        return AdoptAction.IGNORE;
    }

    @Override
    public DiceAction onTurnDiceRolled(Dice[] newDices, int pointsThisRoundSoFar) {
        return new DiceAction(newDices, false);
    }

    @Override
    public void onTurnEnd(boolean successfulRound, int pointsReceived) {

    }

    @Override
    public void onGameEnd(Player[] players, Player wonPlayer) {

    }

    @Override
    public void onError(GameException e) {

    }
}
