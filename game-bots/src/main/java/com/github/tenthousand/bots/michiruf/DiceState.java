package com.github.tenthousand.bots.michiruf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Ruf
 * @since 2018-02-21
 */
// TODO Calculate all selections of the available dices after they where rolles
// And perform on each as state calculation (one must have the best solution)
class DiceState {

    // NOTE Could be just a list!
    private static Map<Integer, DiceState> states;

    public static void initialize(int numberOfDices) {
        states = new HashMap<>();
        // Initialize the states
        for (int i = 1; i <= numberOfDices; i++) {
            DiceState state = new DiceState(i);
            states.put(i, state);
        }
        // Calculate state changes for these states
        for (int i = 1; i <= numberOfDices; i++) {
            DiceStateChange.calculateStateChanges(getForRemainingDices(i));
        }
    }

    public static DiceState getForRemainingDices(int remainingDices) {
        DiceState result = states.get(remainingDices);
        if (result == null) {
            throw new IllegalArgumentException("Dice state for remaining-dices-count does not exist!");
        }
        return result;
    }

    private final int remainingDices;
    private final List<DiceStateChange> stateChanges;

    private DiceState(int remainingDices) {
        this.remainingDices = remainingDices;
        stateChanges = new ArrayList<>();
    }

    public int getRemainingDices() {
        return remainingDices;
    }

    public void addStateChange(DiceStateChange stateChange) {
        stateChanges.add(stateChange);
    }
}
