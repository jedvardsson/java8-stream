package se.cygni.java8.rovarsprak;

import java.util.BitSet;

public class RovarSprak1 {
    private static final BitSet CONSONANTS;

    static {
        CONSONANTS = new BitSet(256);
        for (char c : "bcdfghjklmnpqrstvwxz".toCharArray()) {
            CONSONANTS.set(c);
            CONSONANTS.set(Character.toUpperCase(c));
        }
    }

    public static String rovarSprak1(String s) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (CONSONANTS.get(c)) {
                buf.append(c + "o" + Character.toLowerCase(c));
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }
}
