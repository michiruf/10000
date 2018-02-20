package com.github.michiruf.tenthousand;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author Michael Ruf
 * @since 2018-02-20
 */
public class DicesValueDetectorTest {

    @Test
    public void testSample() {
        DicesValueDetector d = new DicesValueDetector(new Dice[]{
                Dice.ONE, Dice.FIVE, Dice.TWO, Dice.TWO, Dice.TWO, Dice.SIX
        });

        // Dices Count
        Assert.assertEquals(1, d.getDiceCount(Dice.ONE));
        Assert.assertEquals(3, d.getDiceCount(Dice.TWO));
        Assert.assertEquals(0, d.getDiceCount(Dice.THREE));
        Assert.assertEquals(0, d.getDiceCount(Dice.FOUR));
        Assert.assertEquals(1, d.getDiceCount(Dice.FIVE));
        Assert.assertEquals(1, d.getDiceCount(Dice.SIX));

        // Value filter
        Map<Dice, Integer> valuableDicesMap = d.filterValuableDices();
        Assert.assertEquals(1, (int) valuableDicesMap.get(Dice.ONE));
        Assert.assertEquals(3, (int) valuableDicesMap.get(Dice.TWO));
        Assert.assertEquals(1, (int) valuableDicesMap.get(Dice.FIVE));
        Assert.assertNull(valuableDicesMap.get(Dice.SIX));
        Map<Dice, Integer> valuableDicesMapCacheHit = d.filterValuableDices();
        Assert.assertEquals(valuableDicesMap, valuableDicesMapCacheHit);

        // Values
        Assert.assertArrayEquals(new Dice[]{Dice.ONE, Dice.TWO, Dice.TWO, Dice.TWO, Dice.FIVE}, d.getValuableDices());

        // Non value filter
        Map<Dice, Integer> nonValuableDicesMap = d.filterNonValuableDices();
        Assert.assertEquals(1, (int) nonValuableDicesMap.get(Dice.SIX));
        Assert.assertNull(nonValuableDicesMap.get(Dice.ONE));
        Map<Dice, Integer> nonValuableDicesMapCacheHit = d.filterNonValuableDices();
        Assert.assertEquals(nonValuableDicesMap, nonValuableDicesMapCacheHit);

        // Non values
        Assert.assertArrayEquals(new Dice[]{Dice.SIX}, d.getNonValuableDices());

        // Has-er
        Assert.assertTrue(d.hasAValue());
        Assert.assertFalse(d.hasOnlyValues());

        // Contains
        Assert.assertTrue(d.contains(new Dice[]{Dice.ONE}));
        Assert.assertTrue(d.contains(new Dice[]{Dice.TWO, Dice.TWO, Dice.TWO}));
        Assert.assertTrue(d.contains(new Dice[]{Dice.ONE, Dice.SIX, Dice.FIVE}));
        Assert.assertFalse(d.contains(new Dice[]{Dice.FOUR, Dice.SIX, Dice.FIVE}));
        Assert.assertFalse(d.contains(new Dice[]{Dice.THREE}));

        // Points
        Assert.assertEquals(350, d.calculatePoints());
    }

    // NOTE A more complex sample to
    // * hasAValue
    // * hasOnlyValues
    // would be nice

    @Test
    public void testExponentialCalculation() {
        DicesValueDetector d = new DicesValueDetector(new Dice[]{
                Dice.ONE, Dice.ONE, Dice.ONE, Dice.ONE, Dice.ONE, Dice.ONE
        });
        Assert.assertEquals(8000, d.calculatePoints());
    }
}
