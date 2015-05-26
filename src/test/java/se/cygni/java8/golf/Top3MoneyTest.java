package se.cygni.java8.golf;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

public class Top3MoneyTest {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }

    @Test
    public void testBestUsingComparator() {
        Comparator<Golfer> comparator =
                comparing(Golfer::getMoney).reversed();
        golfers.stream()
                .sorted(comparator).limit(3).forEach(System.out::println);
    }
}