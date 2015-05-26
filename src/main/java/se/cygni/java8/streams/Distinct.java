package se.cygni.java8.streams;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Distinct {

    public static <T, K> Collector<T, Map<K, T>, Stream<T>> by(Function<? super T, ? extends K> classifier) {
        return Collector.of(
                LinkedHashMap::new,
                (m, t) -> m.put(classifier.apply(t), t),
                (m1, m2) -> {
                    m1.putAll(m2);
                    return m1;
                },
                m -> m.values().stream());
    }
    
}
