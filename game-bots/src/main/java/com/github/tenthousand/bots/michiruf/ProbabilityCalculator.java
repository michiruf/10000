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
                add.accept(50, _1);                                // 1 * 5er
                add.accept(100, _1);                               // 1 * 1er
                break;
            case 2:
                add.accept(100, Math.pow(_1, 2));                  // 2 * 5er
                add.accept(150, Math.pow(_1, 2) * MyMath.factorial(2)); // 1 * 5er + 1 * 1er
                add.accept(200, Math.pow(_1, 2));                  // 2 * 1er
                break;
            case 3:
                add.accept(200, Math.pow(_1, 3) * MyMath.factorial(3)); // 1 x 1er, 2 x 5er
                add.accept(250, Math.pow(_1, 3) * MyMath.factorial(3)); // 2 x 1er, 1 x 5er
                add.accept(1000, Math.pow(_1, 3));                 // 1000-3er
                add.accept(200, Math.pow(_1, 3));                  // 200-3er
                add.accept(300, Math.pow(_1, 3));                  // 300-3er
                add.accept(400, Math.pow(_1, 3));                  // 400-3er
                add.accept(500, Math.pow(_1, 3));                  // 500-3er
                add.accept(600, Math.pow(_1, 3));                  // 600-3er
                break;
            case 4:
                add.accept(1050, Math.pow(_1, 3) * _1);            // 1000-3er + 5er
                add.accept(1100, Math.pow(_1, 3) * _1);            // 1000-3er + 1er
                add.accept(250, Math.pow(_1, 3) * _1);             // 200-3er + 5er
                add.accept(300, Math.pow(_1, 3) * _1);             // 200-3er + 1er
                add.accept(350, Math.pow(_1, 3) * _1);             // 300-3er + 5er
                add.accept(400, Math.pow(_1, 3) * _1);             // 300-3er + 1er
                add.accept(450, Math.pow(_1, 3) * _1);             // 400-3er + 5er
                add.accept(500, Math.pow(_1, 3) * _1);             // 400-3er + 1er
                add.accept(550, Math.pow(_1, 3) * _1);             // 500-3er + 5er
                add.accept(600, Math.pow(_1, 3) * _1);             // 500-3er + 1er
                add.accept(650, Math.pow(_1, 3) * _1);             // 600-3er + 5er
                add.accept(700, Math.pow(_1, 3) * _1);             // 600-3er + 1er
                add.accept(2 * 1000, Math.pow(_1, 4));             // 1000-4er
                add.accept(2 * 200, Math.pow(_1, 4));              // 200-4er
                add.accept(2 * 300, Math.pow(_1, 4));              // 300-4er
                add.accept(2 * 400, Math.pow(_1, 4));              // 400-4er
                add.accept(2 * 500, Math.pow(_1, 4));              // 500-4er
                add.accept(2 * 600, Math.pow(_1, 4));              // 600-4er
                break;
            case 5:
                // TODO ...
                // This will get pretty complex
        }
        return results;
    }

    // NOTE This should be good!
    public static double calculateFailingProbability(int numberOfNonValueDices) {
        switch (numberOfNonValueDices) {
            case 1:
            case 2:
                return Math.pow(_4, numberOfNonValueDices);
            case 3:
            case 4:
                return Math.pow(_4, 2) * Math.pow(_3, numberOfNonValueDices % 2);
            case 5:
            case 6:
                return Math.pow(_4, 2) * Math.pow(_3, 2) * Math.pow(_2, numberOfNonValueDices % 2);
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
