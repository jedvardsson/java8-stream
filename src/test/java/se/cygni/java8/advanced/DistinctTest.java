package se.cygni.java8.advanced;

import org.junit.Test;
import se.cygni.java8.JsonAssert;
import se.cygni.java8.exercise.Ex1MapAndSumTest;
import se.cygni.java8.golf.Gender;
import se.cygni.java8.golf.Golfer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DistinctTest {

    @Test
    public void testBy() throws Exception {
        List<Golfer> golfers = Ex1MapAndSumTest.createGolfers();

        List<Golfer> actual = golfers.stream()
                .collect(Distinct.by(Golfer::getGender))
                .collect(Collectors.toList());

        LinkedHashMap<Gender, Golfer> expected = new LinkedHashMap<>();
        for (Golfer golfer : golfers) {
            expected.put(golfer.getGender(), golfer);
        }

        JsonAssert.assertJsonEquals(expected.values(), actual);
    }
}