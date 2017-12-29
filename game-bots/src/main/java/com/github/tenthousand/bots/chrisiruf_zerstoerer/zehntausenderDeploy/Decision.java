package com.github.tenthousand.bots.chrisiruf_zerstoerer.zehntausenderDeploy;

import java.util.HashSet;

import lombok.EqualsAndHashCode;
import com.github.tenthousand.bots.chrisiruf_zerstoerer.util.AH;

/**
 * Types of decisions:
 * <ul>
 * <li>Standard turn start: 6, null</li>
 * <li>Adopt with n dice: n, [] with n = 1,2,3,4,5,6</li>
 * <li>During turn: n, [0,a1,a2,a3,a4,a5,a6]</li>
 * </ul>
 */
@EqualsAndHashCode
public class Decision {

    static Decision[] getX(StatePre s) {
        // standard turn start
        if (s.diceOnTable == null) {
            return new Decision[] { new Decision(Zer.NO_DICE, null) };
        }
        // adopt state
        if (s.diceOnTable.length == 1) {
            // here can start in two different states
            // is handled in A()
            throw new Error();

        }
        // during game states can roll all
        if (SM.mayRollAllDice(s.diceOnTable)) {
            return new Decision[] { //
                    new Decision(Zer.NO_DICE, AH.clone(s.diceOnTable)), // roll all
                    new Decision(0, AH.clone(s.diceOnTable)) }; // end turn
        }

        // during game states cannot roll all
        HashSet<Decision> set = new HashSet<Decision>();
        set.add(new Decision(0, AH.clone(s.diceOnTable))); // add stop decision
        // always roll dice without points (slot [0])
        int[] newSpotses = AH.clone(s.diceOnTable);
        int n = newSpotses[0];
        newSpotses[0] = 0;
        actionsRecursive(s, 1, n, newSpotses, set);
        return set.toArray(new Decision[set.size()]);
    }

    static void actionsRecursive(StatePre s, int l, int diceInCup, int[] spotsLeft, HashSet<Decision> set) {
        if (l > 6) { // reached end
            if (diceInCup > 0) { // not roll decision already in list
                // make sure that some dice giving points stay on table
                if (SM.points(spotsLeft) > 0) {
                    Decision d = new Decision(diceInCup, spotsLeft);
                    set.add(d);
                }
            }
            return;
        }

        if (spotsLeft[l] == 0) { // just go to next l
            actionsRecursive(s, l + 1, diceInCup, spotsLeft, set);
        } else {
            if (l == 1 || l == 5) {
                if (spotsLeft[l] >= 3) {
                    leaveAllOnTableTakingPoints(s, l, diceInCup, spotsLeft, set);
                } else {
                    leaveAllOnTableTakingPoints(s, l, diceInCup, spotsLeft, set);
                    leaveOneOutPutOthersIntoCup(s, l, diceInCup, spotsLeft, set);
                    allIntoCupNotTakingPoints(s, l, diceInCup, spotsLeft, set);
                }
            } else { // 2er, 3er, 4er, 6er
                if (spotsLeft[l] < 3) {
                    allIntoCupNotTakingPoints(s, l, diceInCup, spotsLeft, set);
                } else {
                    leaveAllOnTableTakingPoints(s, l, diceInCup, spotsLeft, set);
                    allIntoCupNotTakingPoints(s, l, diceInCup, spotsLeft, set);
                }
            }
        }
    }

    static void allIntoCupNotTakingPoints(StatePre s, int l, int diceInCup, int[] spotsLeft,
            HashSet<Decision> set) {
        int[] newSpotsLeft = AH.clone(spotsLeft);
        newSpotsLeft[l] = 0;
        actionsRecursive(s, l + 1, diceInCup + spotsLeft[l], newSpotsLeft, set);
    }

    static void leaveAllOnTableTakingPoints(StatePre s, int l, int diceInCup, int[] spotsLeft,
            HashSet<Decision> set) {
        actionsRecursive(s, l + 1, diceInCup, spotsLeft, set);
    }

    static void leaveOneOutPutOthersIntoCup(StatePre s, int l, int diceInCup, int[] spotsLeft,
            HashSet<Decision> set) {
        int[] newSpotsLeft = AH.clone(spotsLeft);
        newSpotsLeft[l] = 1;
        actionsRecursive(s, l + 1, diceInCup + spotsLeft[l] - 1, newSpotsLeft, set);
    }

    final int   x;                   // number of dice that we roll, x == 0 ==> stop
    final int[] diceThatStayOnTable; // that have been rolled in this round
                                     // the ones before must already be in points

    Decision(int x, int[] spotses) {
        super();
        this.x = x;
        this.diceThatStayOnTable = spotses;
    }

    boolean isAdoptDecision() {
        return (diceThatStayOnTable != null && diceThatStayOnTable.length == 0);
    }

    public String toString() {
        return "Würfle " + x + " Würfel, lass " + Zer.diceToString(diceThatStayOnTable) + " liegen";
    }
}
