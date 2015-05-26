package se.cygni.java8.golf;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class GroupByContryAndLevelTest {


    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }


    @Test
    public void testGroupByLevelStreamWithOrdering() throws Exception {
        Map<Country, Map<Level, List<Golfer>>> golfersByLevel = golfers.stream()
                .sorted(comparing(Golfer::getHcp))
                .collect(Collectors.groupingBy(
                        Golfer::getCountry,
                        Collectors.groupingBy(
                                Level::getLevel,
                                LinkedHashMap::new,
                                Collectors.toList())));
        golfersByLevel.forEach((country, map) -> {
            System.out.println("==== " + country + " ====");
            map.forEach((level, golfers) -> {
                System.out.println("    ---- " + level + " -----");
                golfers.forEach(g -> System.out.println("    " + g));
            });
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