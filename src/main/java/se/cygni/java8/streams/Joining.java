package se.cygni.java8.streams;

import java.util.stream.Collector;

public class Joining {
    private Joining() {
    }

    public static Collector<String, ?, String> with1(String delimiter) {
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

    public static Collector<String, ?, String> with2(String delimiter) {
        return Collector.of(
                () -> new StringJoiner(delimiter),
                StringJoiner::append,
                StringJoiner::merge,
                StringJoiner::toString);
    }

    public static class StringJoiner {
        private final String delimiter;
        private final StringBuilder sb = new StringBuilder();

        public StringJoiner(String delimiter) {
            this.delimiter = delimiter;
        }

        public void append(String s) {
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(s);
        }

        public StringJoiner merge(StringJoiner j) {
            sb.append(delimiter).append(j.sb);
            return this;
        }

        public String toString() {
            return sb.toString();
        }
    }
}
