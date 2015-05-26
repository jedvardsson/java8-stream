package se.cygni.java8.streams;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BatchTest {

    private List<Integer> numbers;

    @Before
    public void before() throws Exception {
        numbers = IntStream.range(0, 93).mapToObj(i -> i).collect(Collectors.toList());
    }

    @Test
    public void testBatchesCollection() throws Exception {
        int batchSize = 7;
        Stream<Collection<Integer>> batches = Batch.of(numbers, batchSize);
        assertBatches(batches, batchSize, numbers);
    }

    @Test
    public void testParallelBatchesStream() throws Exception {
        int batchSize = 7;
        Stream<Collection<Integer>> batches = Batch.of(numbers.stream(), batchSize).parallel();
        List<Collection<Integer>> list = batches.collect(Collectors.toList());
        list.forEach(System.out::println);
        batches = list.stream();
        assertBatches(batches, batchSize, numbers);
    }

    private <T> void assertBatches(Stream<Collection<T>> batches, int batchSize, List<T> expected) {
        Set<T> actual = batches.filter(c -> c.size() <= batchSize).flatMap(c -> c.stream()).collect(Collectors.toSet());
        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertEquals(new HashSet<>(expected), actual);
    }

    //    @Test
//    public void testPerf() throws Exception {
//        int expectedSize = 1000000;
//        List<Integer> numbers = IntStream.range(0, expectedSize).mapToObj(i -> i).collect(Collectors.toList());
//        benchmark(1000, () -> {
//            int size = Streams.of(numbers, 10000).parallel().mapToInt(c -> c.size()).sum();
//            Assert.assertEquals(expectedSize, size);
//            return size;
//        });
//    }
//
    @Test
    public void testPerf() throws Exception {
        int expectedSize = 1000000;
        double[] numbers = new Random().doubles(expectedSize).toArray();
        benchmark(1000, () -> {
            double min = Double.MAX_VALUE;
            for (double d : numbers) {
                if (d < min) {
                    min = d;
                }
            }
            return min;
        });
        benchmark(1000, () -> Arrays.stream(numbers).min().getAsDouble());
        benchmark(1000, () -> {
            AtomicReference<Double> min = new AtomicReference<>(Double.MAX_VALUE);
            Arrays.stream(numbers).forEach(d -> {
                        double m = min.get();
                        if (d < m) {
                            min.compareAndSet(m, d);
                        }
                    }
            );
            return min.get();
        });

    }

    public static void benchmark(int iterations, Callable<Double> callable) throws Exception {
        long start = System.currentTimeMillis();
        long result = 0;
        for (int i = 0; i < iterations; i++) {
            result += callable.call();
        }
        long dur = System.currentTimeMillis() - start;
        System.out.printf("%.2f ms/iter      %s%n", dur / (double) iterations, result);
    }
}