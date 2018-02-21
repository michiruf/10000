package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.Dice;
import com.github.michiruf.tenthousand.DiceAction;
import com.github.michiruf.tenthousand.DicesValueDetector;

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
        int dp = valueDetector.calculatePoints();
        int sp = dp + pointsThisRoundSoFar;
//        System.out.println("Points " + pointsThisRoundSoFar + " + " + dp + " = " + sp);
        return new DiceAction(valueDetector.getValuableDices(), sp < 250);
    }
}
