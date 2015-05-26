package se.cygni.java8.exercise;

import se.cygni.java8.golf.Golfer;
import se.cygni.java8.golf.Level;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ex2 {

    public static List<Golfer> findGolfersAboveHcp30(List<Golfer> golfers) {
        return golfers.stream().filter(g -> g.getHcp() > 30).collect(Collectors.toList());
    }

    public static List<String> findNamesOfFirst3GolfersAboveHcp30(List<Golfer> golfers) {
        return golfers.stream()
                .filter(g -> g.getHcp() > 30)
                .limit(3)
                .map(Golfer::getName)
                .collect(Collectors.toList());
    }

    public static String joinNamesOfFemaleGolfers(List<Golfer> golfers) {
        return golfers.stream()
                .filter(Golfer::isFemale)
                .map(Golfer::getName)
                .collect(Collectors.joining(", "));
    }
}
