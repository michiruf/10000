package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.Configuration;
import com.github.michiruf.tenthousand.PlayerDecisionInterface;
import com.github.michiruf.tenthousand.PlayerInterface;

/**
 * @author Michael Ruf
 * @since 2017-12-26
 */
public class MichiRufBot implements PlayerInterface {

    @Override
    public void onInitialization() {
        DiceState.initialize(Configuration.NO_DICES);
//        DiceProbability.initialize(Configuration.NO_DICES);
    }

    @Override
    public PlayerDecisionInterface getDecisionInterface() {
        return new MichiRufDecisions();
    }
}
