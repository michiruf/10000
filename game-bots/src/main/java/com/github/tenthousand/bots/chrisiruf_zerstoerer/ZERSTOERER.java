package com.github.tenthousand.bots.chrisiruf_zerstoerer;


import com.github.michiruf.tenthousand.PlayerDecisionInterface;
import com.github.michiruf.tenthousand.PlayerInterface;

public class ZERSTOERER implements PlayerInterface {

    @Override
    public void onInitialization() {
    }

    @Override
    public PlayerDecisionInterface getDecisionInterface() {
        return new ZERSTOERERDecisions();
    }
}
