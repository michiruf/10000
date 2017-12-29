package com.github.tenthousand.bots.chrisiruf_zerstoerer.zehntausenderDeploy;

import java.util.HashSet;

import lombok.EqualsAndHashCode;
import com.github.tenthousand.bots.chrisiruf_zerstoerer.util.AH;
import com.github.tenthousand.bots.chrisiruf_zerstoerer.util.Partition;

/**
 * Types of states:
 * <ul>
 * <li>Standard turn start: 0, 0, null</li>
 * <li>Adopt possibility with k dice and Q points: 0, Q, [k] with Q = 50, 100, ...</li>
 * <li>During turn: P, Q, [a0,a1,a2,a3,a4,a5,a6] with P,Q >= 0 and a with some points</li>
 * </ul>
 */
@EqualsAndHashCode
public class StatePre {

    /** Generate all states that need a decision!!! */
    static StatePre[] getStates() {
        // set because duplicates result from mapping
        HashSet<StatePre> statesTmp = new HashSet<StatePre>();

        // standard turn start
        statesTmp.add(standardStart);

        // adopt states with k dice and q points
        for (int k = 1; k <= 6; k++) {
            for (int q = 50; q <= Zer.MAX_POINTS; q += 50) {
                int[] a = { k };
                statesTmp.add(new StatePre(0, q, a));
            }
        }

        // during turn states
        for (int diceOnTable = Zer.NO_DICE; diceOnTable >= 1; diceOnTable--) {
            int[][] partitions = Partition.partitionOrdered(diceOnTable, 6);
            for (int[] partition : partitions) {
                int[] spotses = new int[7];
                System.arraycopy(partition, 0, spotses, 1, partition.length);
                for (int P = 0; P <= Zer.MAX_POINTS; P += 50) {
                    for (int Q = 0; Q <= Zer.MAX_POINTS; Q += 50) {
                        StatePre sTmp = new StatePre(P, Q, spotses);
                        if (!excludeState(sTmp)) {
                            statesTmp.add(mapToRepresentative(sTmp));
                        }
                    }
                }
            }
        }
        return statesTmp.toArray(new StatePre[statesTmp.size()]);
    }

    static StatePre[] getStatesWithoutAdoptStates() {
        HashSet<StatePre> statesTmp = new HashSet<StatePre>();
        statesTmp.add(standardStart);
        for (int diceOnTable = Zer.NO_DICE; diceOnTable >= 1; diceOnTable--) {
            int[][] partitions = Partition.partitionOrdered(diceOnTable, 6);
            for (int[] partition : partitions) {
                int[] spotses = new int[7];
                System.arraycopy(partition, 0, spotses, 1, partition.length);
                for (int P = 0; P <= Zer.MAX_POINTS; P += 50) {
                    StatePre sTmp = new StatePre(P, 0, spotses);
                    if (!excludeState(sTmp)) {
                        statesTmp.add(mapToRepresentative(sTmp));
                    }
                }
            }
        }
        return statesTmp.toArray(new StatePre[statesTmp.size()]);
    }

    static boolean excludeState(StatePre s) {
        // points limit (make finite)
        if (s.totalPoints() > Zer.MAX_POINTS) return true;

        // no decision required
        if (SM.turnEnd(s.diceOnTable)) return true;

        return false;
    }

    static StatePre mapToRepresentative(StatePre s) {
        if (s.diceOnTable == null) return s;
        if (s.diceOnTable.length == 1) return s;

        // during turn states
        // all dice that do not yield points are added to slot [0] of the spotses array
        int[] newSpotses = AH.clone(s.diceOnTable);
        for (int i : Zer.NIETEN) {
            if (newSpotses[i] > 0 && newSpotses[i] < 3) {
                newSpotses[0] += newSpotses[i];
                newSpotses[i] = 0;
            }
        }
        return new StatePre(s.p, s.q, newSpotses);
    }

    static StatePre standardStart = new StatePre(0, 0, null);

    /**
     * @param s
     *            state in which turn was ended.
     * @return state with according adopt possibility.
     */
    static StatePre takeOverState(StatePre s) {
        if (SM.turnEnd(s.diceOnTable)) throw new Error();
        s = StatePre.mapToRepresentative(s); // double holds better
        // if points too high reduce to last in state space
        int q = Math.min(Zer.MAX_POINTS, s.totalPoints());
        int noDice = s.diceOnTable[0];
        // if alle wieder nei ...
        if (SM.mayRollAllDice(s.diceOnTable)) noDice = 6;
        return new StatePre(0, q, new int[] { noDice });
    }

    final int   p;           // points (excluding points adopted)
    final int   q;           // adopted points
    final int[] diceOnTable; // frequency of dice spots before decision
                             // the index is used for the state mapping
                             // all dice in slot [0] dont yield points

    StatePre(int points, int pointsAdopted, int[] spotses) {
        this.p = points;
        this.q = pointsAdopted;
        this.diceOnTable = spotses;
    }

    /** Clone constructor */
    StatePre(StatePre s) {
        this.p = s.p;
        this.q = s.q;
        this.diceOnTable = AH.clone(s.diceOnTable);
    }

    boolean isAdoptPossibilityState() {
        return diceOnTable != null && diceOnTable.length == 1;
    }

    int totalPoints() {
        if (diceOnTable == null) return 0; // standard turn start state
        if (diceOnTable.length == 1) return 0; // adopt points state
        return p + q + SM.points(diceOnTable);
    }

    public String toString() {
        return p + "," + q + " > " + Zer.diceToString(diceOnTable);
    }
}
