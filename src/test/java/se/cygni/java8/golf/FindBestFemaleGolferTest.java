package se.cygni.java8.golf;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.util.Comparator.comparing;

public class FindBestFemaleGolferTest {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }


    @Test
    public void testBestFemaleGolferTrad() {
        Golfer best = null;
        for (Golfer golfer : golfers) {
            if (golfer.isFemale()) {
                if (best == null || golfer.getHcp() < best.getHcp()) {
                    best = golfer;
                }
            }
        }
        System.out.println(best);
    }

    @Test
    public void testBestFemaleGolferStream() {
        Optional<Golfer> best = golfers.stream()
                .filter(g -> g.isFemale())
                .min((a, b) -> a.getHcp() - b.getHcp());
        System.out.println(best);
    }

    @Test
    public void testBestFemaleGolferStream2() {
        Golfer sanna = new Golfer("Sanna", 1, Gender.FEMALE, Country.SE, 1000);
        Optional<Golfer> best = golfers.stream()
                .filter(Golfer::isFemale)
                .min(comparing(Golfer::getHcp));
        System.out.println(best);
    }
}