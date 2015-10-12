package se.cygni.java8.rovarsprak;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public abstract class RovarSprakTest {


    private final Function<String, String> rovarSprak;

    public RovarSprakTest(Function<String, String> rovarSprak) {
        this.rovarSprak = rovarSprak;
    }

    @Test
    public void testRovarSprak() throws Exception {
        Assert.assertEquals("Rorödoda vovitota rorososenon", rovarSprak.apply("Röda vita rosen"));
    }
}
