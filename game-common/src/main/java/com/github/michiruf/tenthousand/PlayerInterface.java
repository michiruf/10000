package com.github.michiruf.tenthousand;

/**
 * @author Michael Ruf
 * @since 2018-02-18
 */
public interface PlayerInterface {

    /**
     * Called when the player gets initialized. Use this for very long initializations that are non game dependent,
     * because it gets called only once per player and not for every game!
     */
    void onInitialization();

    /**
     * Called to receive the players decision interface.
     * This gets called for every game to have a par game decision interface.
     */
    PlayerDecisionInterface getDecisionInterface();
}
