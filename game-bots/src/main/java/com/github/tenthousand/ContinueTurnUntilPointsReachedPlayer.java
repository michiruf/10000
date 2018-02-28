package com.github.tenthousand;

import com.github.michiruf.tenthousand.*;
import com.github.michiruf.tenthousand.exception.GameException;

/**
 * @author Michael Ruf
 * @since 2018-02-20
 */
public class ContinueTurnUntilPointsReachedPlayer implements PlayerInterface {

    @Override
    public void onInitialization() {
        // Do nothing
    }

    @Override
    public PlayerDecisionInterface getDecisionInterface() {
        return new Decisions();
    }

    private static class Decisions implements PlayerDecisionInterface {

        private Player me;

        @Override
        public void onGameStart(Player[] players, Player self) {
            this.me = self;
        }

        @Override
        public AdoptAction onTurnStart(RoundAdoptionState roundAdoptionState) {
            return AdoptAction.IGNORE;
        }

        @Override
        public DiceAction onTurnDiceRolled(Dice[] newDices, int pointsThisRoundSoFar) {
            // Just enter the game by continuing until there are 1000 points
            if (!me.hasEnteredGame()) {
                DicesValueDetector d = new DicesValueDetector(newDices);
                return new DiceAction(d.getValuableDices(), pointsThisRoundSoFar + d.calculatePoints() < 1000);
            }

            // Continue until the round threshold is reached
            int dp = DicesValueDetector.calculatePoints(newDices);
            int sp = dp + pointsThisRoundSoFar;
            return new DiceAction(DicesValueDetector.getValuableDices(newDices), sp < 250);
        }

        @Override
        public void onTurnEnd(boolean successfulRound, int pointsReceived) {
            // Do nothing
        }

        @Override
        public void onGameEnd(Player[] players, Player[] wonPlayers) {
            // Do nothing
        }

        @Override
        public void onError(GameException e) {
            // This should never print an error
            e.printStackTrace();
        }
    }
}
