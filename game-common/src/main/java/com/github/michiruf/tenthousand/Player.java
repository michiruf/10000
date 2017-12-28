package com.github.michiruf.tenthousand;

/**
 * @author Michael Ruf
 * @since 2017-12-27
 */
public class Player {

    public final String name;
    final PlayerDecisionInterface decisionInterface;

    private int points;

    public Player(String name, PlayerDecisionInterface player) {
        this.name = name;
        this.decisionInterface = player;
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    void addPoints(int points) {
        this.points += points;
    }

    public boolean isEnterGameThresholdReached() {
        return isEnterGameThresholdReached(points);
    }

    public boolean isEnterGameThresholdReached(int points) {
        return points >= Configuration.ENTER_GAME_THRESHOLD;
    }
}
