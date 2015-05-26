package se.cygni.java8.streams;

import org.junit.Test;
import se.cygni.java8.streams.Joining;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class JoiningTest {


    @Test
    public void testJoining1() throws Exception {
        List<String> list = Arrays.asList("Tiger", "Lion", "Hawk", "Bass");
        String s = list.stream().collect(Joining.with1(", "));
        System.out.println(s);
    }

    @Test
    public void testJoining2() throws Exception {
        List<String> list = Arrays.asList("Tiger", "Lion", "Hawk", "Bass");
        String s = list.stream().collect(Joining.with2(", "));
        System.out.println(s);
    }

    @Test
    public void testJoining() {
        List<String> list = IntStream.range(0, 1000).mapToObj(i -> String.valueOf(i)).collect(Collectors.toList());
        String s1 = list.parallelStream().collect(Joining.with1(", "));
        String s2 = list.parallelStream().collect(Joining.with2(", "));

        assertEquals(s1, s2);
    }

    @Test
    public void test() throws Exception {
        Supplier<StringBuilder> supplier = () -> new StringBuilder();

        BiConsumer<StringBuilder, String> accumulator = (sb, s) -> {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(s);
        };

        BinaryOperator<StringBuilder> combiner = (sb1, sb2) -> sb1.append(", ").append(sb2);

        Function<StringBuilder, String> finisher = s -> s.toString();
        StringBuilder sb1 = supplier.get();
        StringBuilder sb2 = supplier.get();
        accumulator.accept(sb1, "Hello");
        accumulator.accept(sb2, "world!");
        sb2 = combiner.apply(sb1, sb2);

        String s = finisher.apply(sb2);
        System.out.println(s);
    }
}
