package se.cygni.java8.streams;

import org.junit.Test;
import se.cygni.java8.golf.Golfer;
import se.cygni.java8.golf.TestUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DistinctTest {

    @Test
    public void testBy() throws Exception {
        List<Golfer> golfers = TestUtil.createGolfers();

        List<Golfer> actual = golfers.stream()
                .collect(Distinct.by(Golfer::getGender))
                .collect(Collectors.toList());

        List<Golfer> expected = Arrays.asList(
                golfers.stream().filter(g -> g.getName().equals("Sanne")).findFirst().get(),
                golfers.stream().filter(g -> g.getName().equals("Arne")).findFirst().get());
        assertEquals(expected, actual);
        System.out.println(actual);
    }
}