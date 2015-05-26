package se.cygni.java8.advanced;

import org.junit.Before;
import org.junit.Test;
import se.cygni.java8.JsonAssert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BatchTest {

    private List<Integer> numbers;

    @Before
    public void before() throws Exception {
        numbers = IntStream.range(0, 93).mapToObj(i -> i).collect(Collectors.toList());
    }

    @Test
    public void testBatchesCollection() throws Exception {
        int batchSize = 7;
        List<List<Integer>> expectedBatches = new ArrayList<>();
        List<Integer> batch = new ArrayList<>();
        for (Integer number : numbers) {
            batch.add(number);
            if (batch.size() >= batchSize) {
                expectedBatches.add(batch);
                batch = new ArrayList<>();
            }
        }
        if (batch.size() > 0) {
            expectedBatches.add(batch);
        }
        List<Collection<Integer>> actualBatches = Batch.of(numbers, batchSize).collect(Collectors.toList());

        JsonAssert.assertJsonEquals(expectedBatches, actualBatches);
    }

}