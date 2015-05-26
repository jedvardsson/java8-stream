package se.cygni.java8.exercise;

import org.junit.Before;
import org.junit.Test;
import se.cygni.java8.golf.Country;
import se.cygni.java8.golf.Golfer;
import se.cygni.java8.golf.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static se.cygni.java8.JsonAssert.assertJsonEquals;

public class Ex4GroupTest {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = Ex1MapAndSumTest.createGolfers();
    }

    @Test
    public void testGroupByLevel() throws Exception {
        Map<Level, List<Golfer>> golfersByLevel = new HashMap<>();
        for (Golfer golfer : golfers) {
            Level level = Level.getLevel(golfer);
            List<Golfer> g = golfersByLevel.get(level);
            if (g == null) {
                g = new ArrayList<>();
                golfersByLevel.put(level, g);
            }
            g.add(golfer);
        }

        assertEquals(golfersByLevel, Ex4Group.groupByLevel(golfers));
    }

    @Test
    public void testGroupByOrderedLevel() throws Exception {
        List<Golfer> sorted = new ArrayList<>(this.golfers);
        Collections.sort(sorted, new Comparator<Golfer>() {
            @Override
            public int compare(Golfer g1, Golfer g2) {
                return Level.getLevel(g1).compareTo(Level.getLevel(g2));
            }
        });

        Map<Level, List<Golfer>> golfersByLevel = new LinkedHashMap<>();
        for (Golfer golfer : sorted) {
            Level level = Level.getLevel(golfer);
            List<Golfer> g = golfersByLevel.get(level);
            if (g == null) {
                g = new ArrayList<>();
                golfersByLevel.put(level, g);
            }
            g.add(golfer);
        }

        assertJsonEquals(golfersByLevel, Ex4Group.groupByOrderedLevel(golfers));
    }

    @Test
    public void testGroupByLevelAndCountryOrderedByHcp() throws Exception {
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

        assertEquals(levelMap, Ex4Group.groupByLevelAndCountry(golfers));
    }
}