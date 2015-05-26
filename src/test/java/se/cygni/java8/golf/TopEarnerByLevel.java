package se.cygni.java8.golf;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TopEarnerByLevel {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }

    @Test
    public void testBestUsingComparator() {
        golfers.stream()
                .collect(Collectors.groupingBy(Level::getLevel, Collectors.maxBy(Comparator.comparing(Golfer::getMoney))))
                .forEach((level, golfer) -> System.out.println(level + "=" + golfer));
    }
}