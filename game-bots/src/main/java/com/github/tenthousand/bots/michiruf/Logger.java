package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.Dice;

import java.util.Arrays;

/**
 * @author Michael Ruf
 * @since 2018-02-23
 */
class Logger {

    private static boolean active;

    public static void initialize(boolean active) {
        Logger.active = active;
    }

    public static void log(Object... o) {
        if (active) {
            StringBuilder sb = new StringBuilder();
            Arrays.stream(o).forEach(os -> sb.append(os.toString()));
            System.out.println(sb.toString());
        }
    }

    public static void logNoNL(Object... o) {
        if (active) {
            Arrays.stream(o).forEach(System.out::print);
        }
    }

    public static void logDices(String s, Dice[] dices) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(dices).forEach(dice -> {
            sb.append(dice.getValue());
            sb.append(" ");
        });
        sb.replace(sb.length() - 1, sb.length(), ""); // Remove the last space
        log(String.format(s, sb));
    }

    public static void logDicesNoNL(String s, Dice[] dices) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(dices).forEach(dice -> {
            sb.append(dice.getValue());
            sb.append(" ");
        });
        sb.replace(sb.length() - 1, sb.length(), ""); // Remove the last space
        logNoNL(String.format(s, sb));
    }
}
