package se.cygni.java8.advanced;

import java.util.stream.Collector;

public class Joining {
    private Joining() {
    }

    public static Collector<String, ?, String> with(String delimiter) {
        return Collector.of(
                StringBuilder::new,                         // Supplier
                (sb, s) -> {                                // Accumulator
                    if (sb.length() > 0) {
                        sb.append(delimiter);
                    }
                    sb.append(s);
                },
                (sb1, sb2) -> sb1.append(", ").append(sb2), // Combiner
                sb -> sb.toString());                       // Finisher
    }
}
