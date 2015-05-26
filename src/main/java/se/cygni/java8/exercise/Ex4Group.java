package se.cygni.java8.exercise;

import se.cygni.java8.golf.Country;
import se.cygni.java8.golf.Golfer;
import se.cygni.java8.golf.Level;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Ex4Group {

    public static Map<Level, List<Golfer>> groupByLevel(List<Golfer> golfers) {
        return golfers.stream().collect(groupingBy(Level::getLevel));
    }

    public static LinkedHashMap<Level, List<Golfer>> groupByOrderedLevel(List<Golfer> golfers) {
        return golfers.stream()
                .sorted(Comparator.comparing(Level::getLevel))
                .collect(groupingBy(Level::getLevel, LinkedHashMap::new, Collectors.toList()));
    }

    public static Map<Level, Map<Country, List<Golfer>>> groupByLevelAndCountry(List<Golfer> golfers) {
        return golfers.stream()
                .collect(groupingBy(Level::getLevel,
                        Collectors.groupingBy(Golfer::getCountry)));
    }

}
