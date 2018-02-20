package com.github.michiruf.tenthousand;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.tenthousand.PlayerContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Michael Ruf
 * @since 2017-12-26
 */
public class Main {

    private static final Object lock = new Object();

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
        List<PlayerInterface> playerInterfaces;
        if (arguments.playerNames != null && arguments.playerNames.size() != 0) {
            playerInterfaces = PlayerContainer.all().stream()
                    .filter(player -> arguments.playerNames.contains(player.getClass().getSimpleName()))
                    .collect(Collectors.toList());
        } else {
            playerInterfaces = PlayerContainer.all();
        }

        // Call the initialization for each player
        playerInterfaces.forEach(PlayerInterface::onInitialization);

        // Create the winner count map
        Map<String, Integer> winnerCounts = new HashMap<>();
        playerInterfaces.forEach(playerInterface -> winnerCounts.put(playerInterface.getClass().getSimpleName(), 0));

        // Create the threaded stuff and start the games
        ExecutorService executor = Executors.newFixedThreadPool(arguments.threadCount);
        for (int i = 0; i < arguments.gamesCount; i++) {
            int finalI = i;
            // Enqueue a game and start it when a thread is ready
            executor.execute(() -> {
                // Start the game and run it until done
                System.out.println("Starting game #" + (finalI + 1));
                Game g = new Game(playerInterfaces.stream()
                        .map(playerInterface -> new Player(
                                playerInterface.getClass().getSimpleName(),
                                playerInterface.getDecisionInterface()))
                        .collect(Collectors.toList())
                        .toArray(new Player[playerInterfaces.size()]));
                g.runGame();
                System.out.println("Ended game #" + (finalI + 1));

                // Increment the win count of the won players
                synchronized (lock) {
                    for (Player p : g.getWonPlayers()) {
                        winnerCounts.put(p.name, winnerCounts.get(p.name) + 1);
                    }
                }
            });
        }

        // Shut down the executor after queueing all stuff and await its termination
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the winners
        System.out.println();
        System.out.println("================= WINNERS =================");
        winnerCounts.forEach((playerName, wins) -> System.out.println(String.format("%s won %d times! (%.1f%%)",
                playerName, wins, 100f * (double) wins / (double) arguments.gamesCount)));
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
