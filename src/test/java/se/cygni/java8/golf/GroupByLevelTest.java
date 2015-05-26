package se.cygni.java8.golf;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class GroupByLevelTest {


    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }


    @Test
    public void testGroupByLevelTrad() throws Exception {
        Map<Level, List<Golfer>> golfersByLevel = new HashMap<>();
        for (Golfer golfer : golfers) {
            Level level = Level.getLevel(golfer);
            List<Golfer> g = golfersByLevel.get(level);
            if (g == null) {
                g = new ArrayList<>();
                golfersByLevel.put(level, g);
            }
            g.add(golfer);
        }
        for (Level level : golfersByLevel.keySet()) {
            System.out.println("---- " + level + " -----");
            for (Golfer golfer : golfersByLevel.get(level)) {
                System.out.println(golfer);
            }
        }
    }

    @Test
    public void testGroupByLevelStream() throws Exception {
        Map<Level, List<Golfer>> golfersByLevel = golfers.stream().
                collect(Collectors.groupingBy(Level::getLevel));

        golfersByLevel.entrySet()
                .stream()
                .flatMap(e -> e.getValue().stream())
                .forEach(g -> System.out.printf("%15s %10s %15s %10s %n", Level.getLevel(g), g.getCountry(), g.getName(), g.getHcp()));
    }

    @Test
    public void testGroupByLevelStreamWithOrdering() throws Exception {
        Map<Level, List<Golfer>> golfersByLevel =
                golfers.stream()
                        .sorted(comparing(Golfer::getHcp))
                        .collect(Collectors.groupingBy(Level::getLevel, LinkedHashMap::new, Collectors.toList()));
        golfersByLevel.forEach((level, golfers) -> {
            System.out.println("---- " + level + " -----");
            golfers.forEach(System.out::println);
        });
    }

    @Test
    public void testDefaultMethods() throws Exception {
        Function<String, Integer> length = s -> s.length();
        Function<Integer, Integer> square = i -> i * i;
        Function<String, Integer> lengthSquared = length.andThen(square);

        System.out.println(lengthSquared.apply("Hello"));
    }
}