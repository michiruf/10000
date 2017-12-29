package com.github.tenthousand.bots.chrisiruf_zerstoerer.zehntausenderDeploy;

import com.github.tenthousand.bots.chrisiruf_zerstoerer.util.AH;
import com.github.tenthousand.bots.chrisiruf_zerstoerer.util.MyMath;

public class SM {

    /**
     * @return points for dice spotses.
     */
    static int points(int[] spotses) {
        int ret = 0;
        if (spotses[1] < 3) {
            ret += spotses[1] * 100;
        } else if (spotses[1] >= 3) {
            ret += comboPoints(spotses[1], 10);
        }
        if (spotses[5] < 3) {
            ret += spotses[5] * 50;
        } else if (spotses[5] >= 3) {
            ret += comboPoints(spotses[5], 5);
        }
        for (int niete : Zer.NIETEN) {
            ret += comboPoints(spotses[niete], niete);
        }
        return ret;
    }

    private static int comboPoints(int n, int base) {
        if (n < 3) return 0;
        return base * 100 * (int) Math.round(Math.pow(2, MyMath.max0(n - 3)));
    }

    static boolean mayRollAllDice(int[] spotses) {
        if (spotses[0] > 0) return false;
        for (int i : Zer.NIETEN) {
            if (spotses[i] > 0 && spotses[i] < 3) return false;
        }
        return true;
    }

    static boolean turnEnd(int[] spotses) {
        if (spotses == null) return false; // standard turn start state
        if (spotses.length == 1) return false; // adopt state
        if (AH.sum(spotses) == 0) return false; // no dice rolled => alle nei
        return points(spotses) == 0;
    }

}
