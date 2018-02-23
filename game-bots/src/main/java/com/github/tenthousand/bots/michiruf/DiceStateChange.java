package com.github.tenthousand.bots.michiruf;

/**
 * @author Michael Ruf
 * @since 2018-02-21
 */
class DiceStateChange {

    public static void calculateStateChanges_OLD(DiceState state, DiceState rootState) {
        for (int i = 1; i <= state.getRemainingDices(); i++) {
            calculatePossibleStateChanges_OLD(state, i);
        }
    }

    private static void calculatePossibleStateChanges_OLD(DiceState previousState, int numberOfDices) {
        // Receive the next state if there is one
        DiceState nextState = null;
        int remainingNumberOfDices = previousState.getRemainingDices() - numberOfDices;
        try {
            nextState = DiceState.getForRemainingDices(remainingNumberOfDices);
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
        DiceState finalNextState = nextState;

        // Calculate probabilities and values for the state change
        ProbabilityCalculator.calculate(numberOfDices, remainingNumberOfDices).forEach(result ->
                previousState.addStateChange(new DiceStateChange(
                        numberOfDices,
                        result.points,
                        result.probability,
                        finalNextState))
        );
    }

    public static void calculateStateChanges(DiceState state, DiceState rootState) {
        for (int i = 1; i <= state.getRemainingDices(); i++) {
            calculatePossibleStateChanges(state, i, rootState);
        }
    }

    private static void calculatePossibleStateChanges(DiceState previousState, int numberOfDices, DiceState rootState) {
        // Receive the next state or the root state
        int remainingNumberOfDices = previousState.getRemainingDices() - numberOfDices;
        DiceState nextState = remainingNumberOfDices != 0 ?
                DiceState.getForRemainingDices(remainingNumberOfDices) :
                rootState;

        // Calculate probabilities and values for the state change
        ProbabilityCalculator.calculate(numberOfDices, remainingNumberOfDices).forEach(result ->
                previousState.addStateChange(new DiceStateChange(
                        numberOfDices,
                        result.points,
                        result.probability,
                        nextState))
        );
    }

    @Deprecated // Can be calculated by a double linked list! (next.dices - previous.dices)
    // TODO getDicesUsed...
    public int dicesUsed;
    public int pointsGot;
    public double probability;
    public DiceState nextState;

    // May be private (again!)?
    public DiceStateChange(int dicesUsed, int pointsGot, double probability, DiceState nextState) {
        this.dicesUsed = dicesUsed;
        this.pointsGot = pointsGot;
        this.probability = probability;
        this.nextState = nextState;
    }


    @Override
    public String toString() {
        return "DiceStateChange{" +
                "dicesUsed=" + dicesUsed + ", " +
                "pointsGot=" + pointsGot + ", " +
                "probability=" + probability + ", " +
                "nextState=" + nextState +
                "}";
    }
}
