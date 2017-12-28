package com.github.michiruf.tenthousand;

/**
 * @author Michael Ruf
 * @since 2017-12-26
 */
public class DiceAction {

    public final Dice[] dicesToKeep;
    public final boolean continueTurn;

    public DiceAction(Dice[] dicesToKeep, boolean continueTurn) {
        this.dicesToKeep = dicesToKeep;
        this.continueTurn = continueTurn;
    }
}
