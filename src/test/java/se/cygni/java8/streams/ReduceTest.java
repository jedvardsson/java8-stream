package se.cygni.java8.streams;

import org.junit.Test;
import se.cygni.java8.golf.Golfer;
import se.cygni.java8.golf.TestUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReduceTest {

    @Test
    public void test2() throws Exception {
        List<Golfer> golfers = TestUtil.createGolfers();
        int sum = golfers.stream()
                .mapToInt(g -> g.getMoney()).sum();
        System.out.println(sum);

        IntSummaryStatistics stats1 = golfers.stream()
                .mapToInt(g -> g.getMoney()).summaryStatistics();
        System.out.println(stats1);

        IntSummaryStatistics stats = golfers.stream()
                .collect(Collectors.summarizingInt(g -> g.getMoney()));

        System.out.println(stats);


    }

    @Test
    public void test1() throws Exception {
        List<Golfer> golfers = TestUtil.createGolfers();
        List<Golfer> collect = golfers.stream()
                .filter(g -> g.getHcp() <= 2)
                .collect(Collectors.toList());
        System.out.println(collect);

        golfers.stream()
                .filter(g -> g.getHcp() <= 2).forEach(System.out::println);


    }

    @Test
    public void test() throws Exception {
        List<Comparator<Golfer>> comparators = Arrays.asList(
                Comparator.comparing(Golfer::getMoney),
                Comparator.comparing(Golfer::getName),
                Comparator.comparing(Golfer::getCountry).reversed()
        );

        {
            Optional<Comparator<Golfer>> compareAll = comparators.stream()
                    .reduce((c1, c2) -> c1.thenComparing(c2));

            Stream<Golfer> golfers = TestUtil.createGolfers().stream();
            if (compareAll.isPresent()) {
                golfers = golfers.sorted(compareAll.get());
            }
        }


        {
            Comparator<Golfer> compareAll = comparators.stream()
                    .reduce((g1, g2) -> 0, (c1, c2) -> c1.thenComparing(c2));
            Stream<Golfer> golfers = TestUtil.createGolfers().stream().sorted(compareAll);
        }


    }
}
