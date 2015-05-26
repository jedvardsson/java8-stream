package se.cygni.java8.golf;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Comparator.comparing;

public class Bottom3MoneyTest {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }

    @Test
    public void testBottom3Money() {
        golfers.stream()
                .sorted(comparing(Golfer::getMoney))
                .limit(3).
                forEach(System.out::println);
    }
}