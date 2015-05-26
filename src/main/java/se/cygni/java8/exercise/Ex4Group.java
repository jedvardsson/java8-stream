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
        return null;
    }

    public static LinkedHashMap<Level, List<Golfer>> groupByOrderedLevel(List<Golfer> golfers) {
        return null;
    }

    public static Map<Level, Map<Country, List<Golfer>>> groupByLevelAndCountry(List<Golfer> golfers) {
        return null;
    }

}
