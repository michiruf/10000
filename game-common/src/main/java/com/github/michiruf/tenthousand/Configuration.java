package com.github.michiruf.tenthousand;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Ruf
 * @since 2017-12-27
 */
public class Configuration {

    public static final int NO_DICES = 6;
    public static final int DICE_PIPS = 6;
    public static final int ENTER_GAME_THRESHOLD = 1000;
    public static final int ROUND_THRESHOLD = 250;
    public static final int WON_GAME_THRESHOLD = 10000;
    public static final Map<Dice, Integer> DICE_VALUES = Collections.unmodifiableMap(new HashMap<Dice, Integer>() {{
        put(Dice.ONE, 100);
        put(Dice.FIVE, 50);
    }});
    public static final Map<Dice, Integer> DICE_TRIPLE_VALUES = Collections.unmodifiableMap(new HashMap<Dice, Integer>() {{
        put(Dice.ONE, 1000);
        put(Dice.TWO, 200);
        put(Dice.THREE, 300);
        put(Dice.FOUR, 400);
        put(Dice.FIVE, 500);
        put(Dice.SIX, 600);
    }});
}
