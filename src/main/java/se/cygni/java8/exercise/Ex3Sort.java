package se.cygni.java8.exercise;

import se.cygni.java8.golf.Golfer;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class Ex3Sort {

    public static List<Golfer> sortByName(List<Golfer> golfers) {
        return golfers.stream()
                .sorted(comparing(Golfer::getName))
                .collect(Collectors.toList());
    }

    public static List<Golfer> sortByHcpAndName(List<Golfer> golfers) {
        Comparator<Golfer> byHcpAndName = comparing(Golfer::getHcp)
                .thenComparing(comparing(Golfer::getName));
        return golfers.stream()
                .sorted(byHcpAndName)
                .collect(Collectors.toList());
    }

    public static Optional<Golfer> findFemaleGolferWithLowestHcp(List<Golfer> golfers) {
        return golfers.stream()
                .filter(Golfer::isFemale)
                .min(comparing(Golfer::getHcp));
    }

    public static List<String> findNamesOfTop3Earners(List<Golfer> golfers) {
        return golfers.stream()
                .sorted(comparing(Golfer::getMoney).reversed())
                .limit(3)
                .map(Golfer::getName)
                .collect(Collectors.toList());
    }
}
