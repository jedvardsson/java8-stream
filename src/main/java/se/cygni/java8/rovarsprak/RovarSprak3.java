package se.cygni.java8.rovarsprak;

import java.util.BitSet;
import java.util.stream.Collectors;

public class RovarSprak3 {
    private static final BitSet CONSONANTS = "bcdfghjklmnpqrstvwxz".chars()
            .collect(() -> new BitSet(256), (b, i) -> {
                b.set(i);
                b.set(Character.toUpperCase(i));
            }, (a, b) -> a.or(b));

    public static String rovarSprak3(String s) {
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
