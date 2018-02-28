package com.github.tenthousand.bots.chrisiruf_zerstoerer;

import com.github.michiruf.tenthousand.PlayerDecisionInterface;
import com.github.michiruf.tenthousand.PlayerInterface;

public class ZERSTOERER implements PlayerInterface {

    static PolicyStateAction pol1000;
    static PolicyStateAction pol250;

    @Override
    public void onInitialization() {
        pol1000 = new PolicyStateAction(1000,
                getClass().getResourceAsStream("/chrisiruf_zerstoerer/pol1000"));
        pol250 = new PolicyStateAction(250, getClass().getResourceAsStream("/chrisiruf_zerstoerer/pol250"));
    }

    @Override
    public PlayerDecisionInterface getDecisionInterface() {
        return new ZERSTOERERDecisions();
    }
}
