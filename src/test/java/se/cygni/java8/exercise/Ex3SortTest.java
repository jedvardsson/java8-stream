package se.cygni.java8.exercise;

import org.junit.Before;
import org.junit.Test;
import se.cygni.java8.golf.Golfer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static se.cygni.java8.JsonAssert.assertJsonEquals;
import static se.cygni.java8.golf.Country.SE;
import static se.cygni.java8.golf.Gender.FEMALE;

public class Ex3SortTest {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = Ex1MapAndSumTest.createGolfers();
    }

    @Test
    public void testSortByName() throws Exception {
        golfers.add(new Golfer(null, 23, FEMALE, SE, 0));
        List<Golfer> expected = new ArrayList<>(this.golfers);
        List<Golfer> actual = Ex3Sort.sortByName(golfers);
        Collections.sort(expected, new Comparator<Golfer>() {
            @Override
            public int compare(Golfer g1, Golfer g2) {
                if (g1.getName() == null) {
                    return -1;
                } else if (g2.getName() == null) {
                    return 1;
                }
                return g1.getName().compareTo(g2.getName());
            }
        });
        assertJsonEquals(expected, actual);
    }

    @Test
    public void testSortByHcpAndName() throws Exception {
        List<Golfer> expected = new ArrayList<>(this.golfers);
        Collections.sort(expected, new Comparator<Golfer>() {
            @Override
            public int compare(Golfer g1, Golfer g2) {
                int i = g1.getHcp() - g2.getHcp();
                if (i == 0) {
                    i = g1.getName().compareTo(g2.getName());
                }
                return i;
            }
        });
        assertJsonEquals(expected, Ex3Sort.sortByHcpAndName(golfers));
    }

    @Test
    public void testFindFemaleGolferWithLowestHcp() throws Exception {
        Golfer min = null;
        for (Golfer golfer : golfers) {
            if (golfer.isFemale()) {
                if (min == null) {
                    min = golfer;
                } else if (golfer.getHcp() < min.getHcp()) {
                    min = golfer;
                }
            }
        }
        assertJsonEquals(min, Ex3Sort.findFemaleGolferWithLowestHcp(golfers).orElse(null));
    }

    @Test
    public void testFindNamesOfTop3Earners() throws Exception {
        List<Golfer> expected = new ArrayList<>(this.golfers);
        Collections.sort(expected, new Comparator<Golfer>() {
            @Override
            public int compare(Golfer g1, Golfer g2) {
                return -(g1.getMoney() - g2.getMoney());
            }
        });
        expected = expected.subList(0, Math.min(expected.size(), 3));
        List<String> expectedNames = new ArrayList<>();
        for (Golfer golfer : expected) {
            expectedNames.add(golfer.getName());
        }
        assertJsonEquals(expectedNames, Ex3Sort.findNamesOfTop3Earners(golfers));
    }
}