package com.github.michiruf.tenthousand;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.tenthousand.PlayerContainer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author Michael Ruf
 * @since 2017-12-26
 */
public class Main {

    public static void main(String[] args) {
        // Parse arguments
        CommandLineArgs arguments = new CommandLineArgs();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        // Show bot names if argument given
        if (arguments.printBots) {
            PlayerContainer.all().forEach(player -> System.out.println(player.getClass().getSimpleName()));
            return;
        }

        // Get the players
        List<PlayerInterface> players;
        if (arguments.playerNames != null && arguments.playerNames.size() != 0) {
            players = PlayerContainer.all().stream()
                    .filter(player -> arguments.playerNames.contains(player.getClass().getSimpleName()))
                    .collect(Collectors.toList());
        } else {
            players = PlayerContainer.all();
        }

        // Initialize the players
        players.forEach(PlayerInterface::onInitialization);

        // Create the threaded stuff and start the games
        ExecutorService executor = Executors.newFixedThreadPool(arguments.threadCount);
        for (int i = 0; i < arguments.gamesCount; i++) {
            int finalI = i;
            // Enqueue a game and start it when ready to execute
            executor.execute(() -> {
                System.out.println("Starting game #" + (finalI + 1));
                Game g = new Game(players.stream()
                        .map(PlayerInterface::getDecisionInterface)
                        .collect(Collectors.toList())
                        .toArray(new PlayerDecisionInterface[players.size()]));
                g.startGame();
                // TODO Get the won player and count!
            });
        }
    }

    private static class CommandLineArgs {

        @Parameter(names = "-bots", description = "Shows a list of available commands")
        boolean printBots = false;

        @Parameter(description = "List of players")
        List<String> playerNames;

        @Parameter(names = "-games", description = "Number of games that shell get simulated")
        int gamesCount = 1;

        @Parameter(names = "-threads", description = "Number of threads multiple games shell run on")
        int threadCount = 1;
    }
}
