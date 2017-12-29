package com.github.tenthousand;

import com.github.michiruf.tenthousand.PlayerDecisionInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Ruf
 * @since 2017-12-27
 */
public final class PlayerContainer {

    public static List<PlayerDecisionInterface> all() {
        List<PlayerDecisionInterface> players = new ArrayList<>();
        players.add(new ConsoleInputPlayer());
        return players;
    }
}
