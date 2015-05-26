package se.cygni.java8.rovarsprak;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static se.cygni.java8.rovarsprak.RovarSprak1.rovarSprak1;
import static se.cygni.java8.rovarsprak.RovarSprak2.rovarSprak2;
import static se.cygni.java8.rovarsprak.RovarSprak3.rovarSprak3;

public class RovarSprakTest {

    @Test
    public void testRovarSprak() throws Exception {
        testRovarSprak("Potatis", "Popototatotisos");
    }

    public void testRovarSprak(String input, String expected) {
        assertEquals(expected, rovarSprak1(input));
        assertEquals(expected, rovarSprak2(input));
        assertEquals(expected, rovarSprak3(input));
    }
}
