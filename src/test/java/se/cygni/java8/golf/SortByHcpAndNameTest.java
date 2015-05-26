package se.cygni.java8.golf;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class SortByHcpAndNameTest {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }


    @Test
    public void sortByHcpAndNameTrad() throws Exception {
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
        System.out.println(golfers);
    }

    @Test
    public void testGroupByLevelStream() throws Exception {
        Comparator<Golfer> orderByHcpAndName =
                Comparator.comparing(Golfer::getHcp).thenComparing(Golfer::getName);
        golfers.stream().sorted(orderByHcpAndName);
    }
}