package se.cygni.java8.exercise;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.cygni.java8.JsonAssert;
import se.cygni.java8.golf.Golfer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Ex2Test {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = Ex1Test.createGolfers();
    }

    @Test
    public void testFindGolferAboveHcp30() throws Exception {
        ArrayList<Golfer> expected = new ArrayList<>();
        for (Golfer golfer : golfers) {
            if (golfer.getHcp() > 30) {
                expected.add(golfer);
            }
        }
        JsonAssert.assertJsonEquals(expected, Ex2.findGolfersAboveHcp30(this.golfers));
    }

    @Test
    public void testFindNamesOfFirst3GolfersAboveHcp30() throws Exception {
        ArrayList<String> expected = new ArrayList<>();
        for (Golfer golfer : golfers) {
            if (golfer.getHcp() > 30) {
                expected.add(golfer.getName());
            }
        }
        JsonAssert.assertJsonEquals(expected, Ex2.findNamesOfFirst3GolfersAboveHcp30(this.golfers));
    }

    @Test
    public void testJoinNamesOfFemaleGolfers() throws Exception {
        StringBuilder buf = new StringBuilder();
        int i = 0;
        for (Golfer golfer : golfers) {
            if (golfer.isFemale()) {
                if (i > 0) {
                    buf.append(", ");
                }
                buf.append(golfer.getName());
                i++;
            }
        }
        Assert.assertEquals(buf.toString(), Ex2.joinNamesOfFemaleGolfers(this.golfers));
    }
}