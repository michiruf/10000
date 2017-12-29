package com.github.tenthousand.bots.chrisiruf_zerstoerer.zehntausenderDeploy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.github.tenthousand.bots.chrisiruf_zerstoerer.util.AH;

public class Zer {

    final static int        MAX_POINTS = 2400;                                  // inclusive
    final static int        NO_DICE    = 6;
    final static int[]      NIETEN     = { 2, 3, 4, 6 };

    static String diceToString(int[] diceFreq) {
        if (diceFreq == null || diceFreq.length != NO_DICE + 1) return AH.toString(diceFreq, "", "", "");
        int nulpen = 0;
        String ret = "";
        if (diceFreq[1] > 0) ret += diceFreq[1] + " 1er, ";
        if (diceFreq[5] > 0) ret += diceFreq[5] + " 5er, ";
        for (int n : Zer.NIETEN) {
            if (diceFreq[n] >= 3) ret += diceFreq[n] + " " + n + "er, ";
            else nulpen += diceFreq[n];
        }
        ret += nulpen + " Nulpen --> " + SM.points(diceFreq) + " points";
        return ret;
    }

    static void writeln(String str, String path) {
        try {
            File logFile = new File(path);
            FileWriter fstream;
            fstream = new FileWriter(logFile, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(str);
            out.write("\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(str);
    }

}
