package com.github.tenthousand;

import com.github.michiruf.tenthousand.*;
import com.github.michiruf.tenthousand.exception.GameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Michael Ruf
 * @since 2017-12-28
 */
public class ConsoleInputPlayer implements PlayerInterface, PlayerDecisionInterface {

    private Player[] players;

    @Override
    public void onInitialization() {
        // Do nothing
    }

    @Override
    public PlayerDecisionInterface getDecisionInterface() {
        // We can return the own instance since no fields are used for logic stuff
        // TODO Might not be correct if we want to play 2 games with a console player..
        // ... Whatever!
        return this;
    }

    @Override
    public void onGameStart(Player[] players, Player self) {
        this.players = players;
        System.out.println("============= PLAYERS =============");
        for (Player player : players) {
            System.out.println(player.name);
        }
    }

    @Override
    public AdoptAction onTurnStart(RoundAdoptionState s) {
        System.out.println("=========== ROUND-START ===========");
        System.out.print("Players:\t");
        for (Player player : players) {
            System.out.print(String.format("%d, ", player.getPoints()));
        }
        System.out.println();
        System.out.println(String.format("Adoption:\t%d points, %d dices ",
                s.adoptedPoints, s.adoptedNumberOfDicesRemaining));
        System.out.println("Adopt this?\t");
        if ("y".equals(readString())) {
            return AdoptAction.ADOPT;
        }
        return AdoptAction.IGNORE;
    }

    @Override
    public DiceAction onTurnDiceRolled(Dice[] newDices, int pointsThisRoundSoFar) {
        System.out.println("============== DICES ==============");
        System.out.println(String.format("Points this round so far: %d", pointsThisRoundSoFar));
        for (Dice newDice : newDices) {
            System.out.print(String.format("%d, ", newDice.getValue()));
        }
        System.out.println();
        System.out.println("Select dices to take (by value, comma separated):\t");

        List<Dice> actionDices = new ArrayList<>();

        String dices = readString();
        if (dices.length() > 0) {
            for (String s : dices.split(",")) {
                actionDices.add(Dice.fromSpot(Integer.parseInt(s)));
            }
        }
        System.out.println("Continue?\t");
        boolean continu = readString().contains("y");
        return new DiceAction(actionDices.toArray(new Dice[actionDices.size()]), continu);
    }

    @Override
    public void onTurnEnd(boolean successfulRound, int pointsReceived) {
        System.out.println(String.format("Round-successful (got points):\t%b", successfulRound));
        System.out.println("///////////////////////////////////");
    }

    @Override
    public void onGameEnd(Player[] players, Player wonPlayer) {
        System.out.println("Game Over!");
    }

    @Override
    public void onError(GameException e) {
        System.err.println(String.format("Error: %s", e.getMessage()));
    }

    private static Scanner s = new Scanner(System.in);

    private static String readString() {
        return s.nextLine();
    }
}
