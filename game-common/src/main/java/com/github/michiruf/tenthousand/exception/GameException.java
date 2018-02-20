package com.github.michiruf.tenthousand.exception;

import com.github.michiruf.tenthousand.Player;

/**
 * @author Michael Ruf
 * @since 2017-12-28
 */
public class GameException extends Exception {

    private Player player;

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Player player) {
        super(message);
        this.player = player;
    }

    public boolean isPlayerFail() {
        return player != null;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String getMessage() {
        if (player == null) {
            return super.getMessage();
        }
        return String.format("[%s] %s", player.name, super.getMessage());
    }
}
