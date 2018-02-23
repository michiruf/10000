package com.github.tenthousand.bots.michiruf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Michael Ruf
 * @since 2018-02-22
 */
class ProbabilityCalculator {

    private static final double _1 = 1.0 / 6.0;
    private static final double _2 = 2 * _1;
    private static final double _3 = 3 * _1;
    private static final double _4 = 4 * _1;
    private static final double _5 = 5 * _1;

    public static List<Result> calculate(int numberOfValueDices, int numberOfNonValueDices) {
        List<Result> results = new ArrayList<>();
        BiConsumer<Integer, Double> add = (points, probability) ->
//                results.add(new Result(points, probability * calculateFailingProbability(numberOfNonValueDices))); // TODO Failing is just a test for now
                results.add(new Result(points, probability));
        switch (numberOfValueDices) {
            case 1:
                add.accept(50, _1);                                     // 1-5er
                add.accept(100, _1);                                    // 1-1er
                break;
            case 2:
                add.accept(100, Math.pow(_1, 2));                       // 2-5er
                add.accept(150, Math.pow(_1, 2) * MyMath.factorial(2)); // 1-5er + 1-1er
                add.accept(200, Math.pow(_1, 2));                       // 2-1er
                break;
            case 3:
                add.accept(200, Math.pow(_1, 3) * MyMath.factorial(3)); // 1-1er, 2-5er
                add.accept(250, Math.pow(_1, 3) * MyMath.factorial(3)); // 2-1er, 1-5er
                add.accept(1000, Math.pow(_1, 3));                      // 3-1er
                add.accept(200, Math.pow(_1, 3));                       // 3-2er
                add.accept(300, Math.pow(_1, 3));                       // 3-3er
                add.accept(400, Math.pow(_1, 3));                       // 3-4er
                add.accept(500, Math.pow(_1, 3));                       // 3-5er
                add.accept(600, Math.pow(_1, 3));                       // 3-6er
                break;
            case 4:
                add.accept(1050, Math.pow(_1, 3) * _1);                 // 3-1er + 5er
                add.accept(1100, Math.pow(_1, 3) * _1);                 // 3-1er + 1er
                add.accept(250, Math.pow(_1, 3) * _1);                  // 3-2er + 5er
                add.accept(300, Math.pow(_1, 3) * _1);                  // 3-2er + 1er
                add.accept(350, Math.pow(_1, 3) * _1);                  // 3-3er + 5er
                add.accept(400, Math.pow(_1, 3) * _1);                  // 3-3er + 1er
                add.accept(450, Math.pow(_1, 3) * _1);                  // 3-4er + 5er
                add.accept(500, Math.pow(_1, 3) * _1);                  // 3-4er + 1er
                add.accept(550, Math.pow(_1, 3) * _1);                  // 3-5er + 5er
                add.accept(600, Math.pow(_1, 3) * _1);                  // 3-5er + 1er
                add.accept(650, Math.pow(_1, 3) * _1);                  // 3-6er + 5er
                add.accept(700, Math.pow(_1, 3) * _1);                  // 3-6er + 1er
                add.accept(2 * 1000, Math.pow(_1, 4));                  // 4-1er
                add.accept(2 * 200, Math.pow(_1, 4));                   // 4-2er
                add.accept(2 * 300, Math.pow(_1, 4));                   // 4-3er
                add.accept(2 * 400, Math.pow(_1, 4));                   // 4-4er
                add.accept(2 * 500, Math.pow(_1, 4));                   // 4-5er
                add.accept(2 * 600, Math.pow(_1, 4));                   // 4-6er
                break;
            case 5:
                // TODO ...
                // This will get pretty complex
        }
        return results;
    }

    public static double calculateFailingProbability(int numberOfDices) {
        switch (numberOfDices) {
            case 1:
                // 4 / 6
                return 2.0 / 3.0;
            case 2:
                // 16 / 36
                return 16.0 / 36.0;
            case 3:
                // 60 / 216
                return 5.0 / 18.0;
            case 4:
                // 204 / 1296
                return 17.0 / 108.0;
            case 5:
                // 600 / 7776
                return 25.0 / 324.0;
            case 6:
                // 1440 / 46656
                return 1440.0 / 46656.0;
        }
        // Default should be 1 because this probability decreased the more dices there are
        return 1;
    }

    // NOTE This is not correct yet...
    public static double calculateFailingProbability2(int numberOfDices) {
        switch (numberOfDices) {
            case 1:
            case 2:
                return Math.pow(_4, numberOfDices);
            case 3:
            case 4:
                return Math.pow(_4, 2) * Math.pow(_3, numberOfDices % 2 + 1);
            case 5:
            case 6:
                return Math.pow(_4, 2) * Math.pow(_3, 2) * Math.pow(_2, numberOfDices % 2 + 1);
        }
        // Default should be 1 because this probability decreased the more dices there are
        return 1;
    }

    public static class Result {

        public final Integer points;
        public final Double probability;

        private Result(Integer points, Double probability) {
            this.points = points;
            this.probability = probability;
        }
    }
}
