package se.cygni.java8.rovarsprak;

import java.util.BitSet;
import java.util.stream.Collectors;

public class RovarSprak2 {
    private static final BitSet CONSONANTS;

    static {
        CONSONANTS = new BitSet(256);
        for (char c : "bcdfghjklmnpqrstvwxz".toCharArray()) {
            CONSONANTS.set(c);
            CONSONANTS.set(Character.toUpperCase(c));
        }
    }

    public static String rovarSprak2(String s) {
        return s.chars()
                .mapToObj(i -> translate((char) i))
                .collect(Collectors.joining(""));
    }

    private static String translate(char c) {
        return CONSONANTS.get(c) ?
                c + "o" + (Character.toLowerCase(c)) :
                c + "";
    }


}
