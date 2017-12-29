package com.github.tenthousand.bots.chrisiruf_zerstoerer.zehntausenderDeploy;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.github.tenthousand.bots.chrisiruf_zerstoerer.util.AH;

public class PolicyStateAction {

    int                         threshold;
    HashMap<StatePre, Decision> A;

    PolicyStateAction(InputStream path) {
        A = new HashMap<>();
        loadFromFile(path);
    }

    Decision A(StatePre s) {
        if (s.totalPoints() > Zer.MAX_POINTS) {
            return new Decision(0, s.diceOnTable);
        }
        if (!A.containsKey(s)) throw new Error();
        return A.get(s);
    }

    void loadFromFile(InputStream path) {
        List<String> lines = readLines(path);
        for (int i = 0; i < lines.size(); i++) {
            String[] sStr = lines.get(i).split("Y")[0].split(";");
            int p = Integer.parseInt(sStr[0]);
            int q = Integer.parseInt(sStr[1]);
            int[] diceOnTable = readDiceArray(sStr[2]);
            StatePre s = new StatePre(p, q, diceOnTable);

            String[] xStr = lines.get(i).split("Y")[1].split(";");
            int x = Integer.parseInt(xStr[0]);
            int[] diceLeft = null;
            if (xStr.length == 1) { // adopt decision
                diceLeft = new int[0];
            } else {
                diceLeft = readDiceArray(xStr[1]);
            }
            Decision dec = new Decision(x, diceLeft);
            A.put(s, dec);
        }
    }

    private static List<String> readLines(InputStream path) {
        try {
            ArrayList<String> result = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                char c;
                try {
                    c = line.charAt(0);
                    if (c != '-' && c != ' ') {
                        result.add(line.trim());
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    // do nothing (empty line)
                }
            }
            reader.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    private int[] readDiceArray(String str) {
        if (str.equals("null")) {
            return null;
        }
        String[] splitted = str.split(",");
        if (splitted.length == 1) {
            return new int[] { Integer.parseInt(splitted[0]) };
        }
        int[] dice = new int[7];
        for (int j = 0; j < 7; j++) {
            dice[j] = Integer.parseInt(splitted[j]);
        }
        return dice;
    }

}
