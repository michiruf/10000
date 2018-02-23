package com.github.tenthousand.bots.michiruf;

import com.github.michiruf.tenthousand.Dice;
import com.github.michiruf.tenthousand.DicesValueDetector;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Ruf
 * @since 2018-02-22
 */
public class ProbabilityCalculatorTest {

    @Test
    public void calculateFailingProbabilityTest1() {
        testCalculateSpecificFailingProbability(1);
    }

    @Test
    public void calculateFailingProbabilityTest2() {
        testCalculateSpecificFailingProbability(2);
    }

    @Test
    public void calculateFailingProbabilityTest3() {
        testCalculateSpecificFailingProbability(3);
    }

    @Test
    public void calculateFailingProbabilityTest4() {
        testCalculateSpecificFailingProbability(4);
    }

    @Test
    public void calculateFailingProbabilityTest5() {
        testCalculateSpecificFailingProbability(5);
    }

    @Test
    public void calculateFailingProbabilityTest6() {
        testCalculateSpecificFailingProbability(6);
    }

    @SuppressWarnings("Duplicates") // Because its a test
    private void testCalculateSpecificFailingProbability(int numDices) {
        List<Dice[]> diceCombinations = new ArrayList<>();
        for (int i = 0; i < Math.pow(6, numDices); i++) {
            Dice[] diceCombination = new Dice[numDices];
            for (int j = 0; j < numDices; j++) {
                diceCombination[j] = Dice.fromSpot(i / (int) Math.pow(6, j) % 6 + 1);
            }
            diceCombinations.add(diceCombination);
        }

        System.out.println("============================================");
        System.out.println("Test with " + numDices + " dices");
        System.out.println("============================================");
        int noValueCount = testCalculateSpecificFailingProbabilityCalculateCombinations(diceCombinations);
        double percentageNoValue = (double) noValueCount / (double) diceCombinations.size();
        Assert.assertEquals(percentageNoValue, DiceProbability.calculateFailingProbability(numDices), 0.00001);
    }

    private int testCalculateSpecificFailingProbabilityCalculateCombinations(List<Dice[]> diceCombinations) {
        final int[] noValueCount = {0};
        diceCombinations.forEach(diceCombination -> {
            for (Dice d : diceCombination) {
                System.out.print(d.getValue() + " ");
            }

            DicesValueDetector d = new DicesValueDetector(diceCombination);
            if (!d.hasAValue()) {
                System.out.print(" no value");
                noValueCount[0]++;
            }

            System.out.println();
        });
        return noValueCount[0];
    }
}
