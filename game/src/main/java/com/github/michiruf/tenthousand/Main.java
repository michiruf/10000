package com.github.michiruf.tenthousand;

import com.github.tenthousand.PlayerContainer;

import java.util.List;

/**
 * @author Michael Ruf
 * @since 2017-12-26
 */
public class Main {

    public static void main(String[] args) {
        List<PlayerDecisionInterface> players = PlayerContainer.all();
        // TODO Filter list by args

        Game g = new Game(players.toArray(new PlayerDecisionInterface[players.size()]));
        g.startGame();
    }
}
