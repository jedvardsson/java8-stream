package se.cygni.java8.advanced;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JoiningTest {

    @Test
    public void testJoining() throws Exception {
        List<String> list = Arrays.asList("Tiger", "Lion", "Hawk", "Bass");
        String s = list.stream().collect(Joining.with(", "));
        Assert.assertEquals("Tiger, Lion, Hawk, Bass", s);
    }

}
