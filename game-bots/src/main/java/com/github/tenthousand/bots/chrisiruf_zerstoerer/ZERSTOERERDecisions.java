package com.github.tenthousand.bots.chrisiruf_zerstoerer;

import java.util.ArrayList;

import com.github.michiruf.tenthousand.AdoptAction;
import com.github.michiruf.tenthousand.Dice;
import com.github.michiruf.tenthousand.DiceAction;
import com.github.michiruf.tenthousand.Player;
import com.github.michiruf.tenthousand.PlayerDecisionInterface;
import com.github.michiruf.tenthousand.RoundAdoptionState;
import com.github.michiruf.tenthousand.exception.GameException;

class ZERSTOERERDecisions implements PlayerDecisionInterface {


    PolicyStateAction pol;          // active policy

    Player[]          players;
    Player            me;

    // game
    int               totalPoints;

    // current turn
    int               pointsAdopted;

    @Override
    public void onGameStart(Player[] players, Player self) {
        this.players = players;
        this.me = self;
        pol = ZERSTOERER.pol1000;
        totalPoints = 0;
        pointsAdopted = 0;
        System.err.println("Fuck yeah!");
        System.err.println("Wuuuuuhuuuuuuuuuuu!");
    }

    @Override
    public AdoptAction onTurnStart(RoundAdoptionState roundAdoptionState) {
        if (roundAdoptionState.isAdoptionAvailable() && roundAdoptionState.adoptedPoints <= me.getPoints()) {
            StatePre s = new StatePre(0, roundAdoptionState.adoptedPoints,
                    new int[] { roundAdoptionState.adoptedNumberOfDicesRemaining });
            Decision dec = pol.A(s);
            if (dec.isAdoptDecision()) {
                pointsAdopted = roundAdoptionState.adoptedPoints;
                return AdoptAction.ADOPT;
            } else {
                pointsAdopted = 0;
                return AdoptAction.IGNORE;
            }
        }
        return AdoptAction.IGNORE;
    }

    @Override
    public DiceAction onTurnDiceRolled(Dice[] newDices, int pointsTurn) {
        int[] diceOnTable = toDice(newDices);
        StatePre s = new StatePre(pointsTurn, pointsAdopted, diceOnTable);
        s = StatePre.mapToRepresentative(s);
        Decision x = pol.A(s);
        if (x.x == 0) { // end turn
            pointsAdopted = 0;
            return new DiceAction(toDiceIgnoreBitches(x.diceThatStayOnTable), false);
        }
        return new DiceAction(toDiceIgnoreBitches(x.diceThatStayOnTable), true);
    }

    private Dice[] toDiceIgnoreBitches(int[] dice) {
        ArrayList<Dice> ret = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            for (int n = 0; n < dice[i]; n++) {
                ret.add(Dice.fromSpot(i));
            }
        }
        return ret.toArray(new Dice[ret.size()]);
    }

    private int[] toDice(Dice[] dice) {
        int[] ret = new int[7];
        for (Dice d : dice) {
            ret[d.getValue()]++;
        }
        return ret;
    }

    @Override
    public void onTurnEnd(boolean successfulRound, int pointsReceived) {
        if (me.getPoints() >= 1000) pol = ZERSTOERER.pol250; // switch policy
    }

    @Override
    public void onGameEnd(Player[] players, Player[] wonPlayers) {
        // Do nothing
    }

    @Override
    public void onError(GameException e) {
        System.err.println("Scheisse: " + e.getMessage());
        e.printStackTrace();
    }

}
