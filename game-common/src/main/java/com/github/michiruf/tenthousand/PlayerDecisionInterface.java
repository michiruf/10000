package com.github.michiruf.tenthousand;

import com.github.michiruf.tenthousand.exception.GameException;

/**
 * @author Michael Ruf
 * @since 2017-12-26
 */
public interface PlayerDecisionInterface {

    /**
     * Called when the game starts. Use this for game depending long initializations.
     *
     * @param players List of players that started the game
     *                Contains for example also information about points
     * @param self    The player object for this decision interface
     *                Contains player relevant data
     */
    void onGameStart(Player[] players, Player self);

    /**
     * Called when each turn starts for the given player.
     *
     * @param roundAdoptionState Parameters for the current round that starts
     * @return Decision what to do
     */
    AdoptAction onTurnStart(RoundAdoptionState roundAdoptionState);

    /**
     * Called when the dice was rolled and new information is available to the player
     * if there were some dices of value.
     *
     * @param newDices             Results of the dices for the new roll
     * @param pointsThisRoundSoFar Points the user made so far in the current round
     * @return Decision what to do next
     */
    DiceAction onTurnDiceRolled(Dice[] newDices, int pointsThisRoundSoFar);

    /**
     * Called when the round is over.
     *
     * @param successfulRound False, when the round is lost ("NIIIIIX")
     * @param pointsReceived  Points the user received this turn
     */
    void onTurnEnd(boolean successfulRound, int pointsReceived);

    /**
     * Called when the game ends and the winner is decided.
     *
     * @param players    List of players with their states when the game ended
     * @param wonPlayers The players that won the game
     */
    void onGameEnd(Player[] players, Player[] wonPlayers);

    /**
     * Called when a non valid decision was told to the game.
     *
     * @param e Exception that was thrown by the game
     */
    void onError(GameException e);
}
