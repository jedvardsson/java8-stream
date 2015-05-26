package se.cygni.java8.advanced;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Distinct {

    public static <T, K> Collector<T, Map<K, T>, Stream<T>> by(Function<? super T, ? extends K> classifier) {
        return null;
    }
    
}
