package com.github.tenthousand;

import com.github.michiruf.tenthousand.PlayerInterface;
import com.github.tenthousand.bots.chrisiruf_zerstoerer.ZERSTOERER;
import com.github.tenthousand.bots.michiruf.MichiRufBot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Ruf
 * @since 2017-12-27
 */
public final class PlayerContainer {

    public static List<PlayerInterface> all() {
        List<PlayerInterface> players = new ArrayList<>();
        players.add(new DoNothingPlayer());
        players.add(new ContinueTurnUntilPointsReachedPlayer());
        players.add(new ConsoleInputPlayer());
        players.add(new MichiRufBot());
        players.add(new ZERSTOERER());
        return players;
    }
}
