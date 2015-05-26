package se.cygni.java8.golf;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class SortedNestedGroupByTest {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }

    @Test
    public void testSortedNestedGroupByTrad() throws Exception {
        golfers.sort(new Comparator<Golfer>() {
            @Override
            public int compare(Golfer o1, Golfer o2) {
                int i = o1.getHcp() - o2.getHcp();
                if (i == 0) {
                    return o1.getName().compareTo(o2.getName());
                }
                return i;
            }
        });

        Map<Level, Map<Country, List<Golfer>>> levelMap = new HashMap<>();
        for (Golfer golfer : golfers) {
            Level level = Level.getLevel(golfer);
            Map<Country, List<Golfer>> countryMap = levelMap.get(level);
            if (countryMap == null) {
                countryMap = new HashMap<>();
                levelMap.put(level, countryMap);
            }
            List<Golfer> list = countryMap.get(golfer.getCountry());
            if (list == null) {
                list = new ArrayList<>();
                countryMap.put(golfer.getCountry(), list);
            }
            list.add(golfer);
        }
        TestUtil.prettyPrint(levelMap);
    }

    @Test
    public void testSortedNestedGroupByStream() throws Exception {
        Comparator<Golfer> orderByHcpAndName =
                Comparator.comparing(Golfer::getHcp)
                        .thenComparing(Golfer::getName);
        Map<Level, Map<Country, List<Golfer>>> levelMap = golfers.stream()
                .sorted(orderByHcpAndName)
                .collect(groupingBy(Level::getLevel,
                        groupingBy(Golfer::getCountry)));
//        TestUtil.prettyPrint(levelMap);
        levelMap.entrySet()
                .stream()
                .flatMap(e -> e.getValue().entrySet().stream())
                .flatMap(e -> e.getValue().stream())
                .forEach(g -> System.out.printf("%15s %15s %15s %15s %n", Level.getLevel(g), g.getCountry(), g.getName(), g.getHcp()));
    }
}