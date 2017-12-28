package com.github.michiruf.tenthousand;

import com.github.michiruf.tenthousand.exception.GameException;

/**
 * @author Michael Ruf
 * @since 2017-12-26
 */
public interface PlayerDecisionInterface {

    /**
     * Called when the game starts. Use this for more long initializations.
     *
     * @param players List of players that started the game
     *                Contains for example also information about points
     */
    void onGameStart(Player[] players);

    /**
     * Called when each turn starts for the given player.
     *
     * @param roundAdoptionState Parameters for the current round that starts
     * @return Decision what to do
     */
    AdoptAction onTurnStart(RoundAdoptionState roundAdoptionState);

    /**
     * Called when the dice was rolled and new information is available to the player.
     *
     * @param newDices Results of the dices for the new roll
     * @return Decision what to do next
     */
    DiceAction onTurnDiceRolled(Dice[] newDices);

    /**
     * Called when the round is over.
     *
     * @param successfulRound False, when the round is lost ("NIIIIIX")
     */
    void onTurnEnd(boolean successfulRound);

    /**
     * Called when the game ends and the winner is decided.
     *
     * @param players   List of players with their states when the game ended
     * @param wonPlayer The player that won the game
     */
    void onGameEnd(Player[] players, Player wonPlayer);

    /**
     * Called when a non valid decision was told to the game.
     *
     * @param e Eception that was thrown by the game
     */
    void onError(GameException e);
}
