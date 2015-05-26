package se.cygni.java8.golf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class FindSumMoneyInCountryTest {
    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }

    @Test
    public void test() throws Exception {
        int sum = golfers.stream()
                .filter(g -> g.getCountry() == Country.NO)
                .mapToInt(g -> g.getMoney()).sum();
        Assert.assertEquals(1048, sum);
        System.out.println(sum);
    }
}
