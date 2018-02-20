package com.github.michiruf.tenthousand;

/**
 * @author Michael Ruf
 * @since 2017-12-27
 */
public class Player {

    public final String name;
    final PlayerDecisionInterface decisionInterface;

    private int points;
    private boolean enteredGame;

    public Player(String name, PlayerDecisionInterface player) {
        this.name = name;
        decisionInterface = player;
        points = 0;
        enteredGame = false;
    }

    public int getPoints() {
        return points;
    }

    void addPoints(int points) {
        if (isEnterGameThresholdReached(points)) {
            enteredGame = true;
        }
        if (enteredGame) {
            this.points += points;
        }
    }

    public boolean hasEnteredGame() {
        return enteredGame;
    }

    public boolean isEnterGameThresholdReached(int points) {
        return points >= Configuration.ENTER_GAME_THRESHOLD;
    }
}
