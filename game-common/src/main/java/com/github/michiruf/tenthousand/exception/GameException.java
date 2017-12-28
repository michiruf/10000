package com.github.michiruf.tenthousand.exception;

/**
 * @author Michael Ruf
 * @since 2017-12-28
 */
public class GameException extends Exception {

    private final boolean playerFail;

    public GameException(String message) {
        this(message, true);
    }

    public GameException(String message, boolean playerFail) {
        super(message);
        this.playerFail = playerFail;
    }

    public boolean isPlayerFail() {
        return playerFail;
    }
}
