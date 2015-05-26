package se.cygni.java8.golf;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class MoneyByCountryTest {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }

    @Test
    public void testBestUsingComparator() {
        golfers.stream()
                .collect(Collectors.groupingBy(Golfer::getCountry, Collectors.summarizingInt(Golfer::getMoney)))
                .forEach((country, sum) -> System.out.println(country + "=" + sum));
    }
}