package se.cygni.java8.advanced;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Batch {

    private Batch() {
    }

    /**
     * Returns a stream of collections of batchSize or smaller.
     */
    public static <T> Stream<Collection<T>> of(Collection<T> collection, int batchSize) {
        return of(collection.spliterator(), batchSize);
    }

    public static <T> Stream<Collection<T>> of(Stream<T> stream, int batchSize) {
        return of(stream.spliterator(), batchSize);
    }

    public static <T> Stream<Collection<T>> of(Spliterator<T> spliterator, int batchSize) {
        return StreamSupport.stream(new BatchSpliterator<>(spliterator, batchSize), false);
    }

    private static class BatchSpliterator<T> implements Spliterator<Collection<T>> {

        private final int batchSize;
        private final Spliterator<T> spliterator;

        public BatchSpliterator(Spliterator<T> spliterator, int batchSize) {
            this.batchSize = batchSize;
            this.spliterator = spliterator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Collection<T>> action) {
            List<T> batch = new ArrayList<>((int) Math.min(batchSize, spliterator.estimateSize()));
            int count = 0;
            while (count < batchSize && spliterator.tryAdvance(batch::add)) {
                count++;
            }
            boolean applyAction = batch.size() > 0;
            if (applyAction) {
                action.accept(batch);
            }
            return applyAction;
        }

        @Override
        public Spliterator<Collection<T>> trySplit() {
            Spliterator<T> other = spliterator.trySplit();
            if (other == null) {
                return null;
            }
            return new BatchSpliterator<>(other, batchSize);
        }

        @Override
        public long estimateSize() {
            long l = spliterator.estimateSize();
            if (l == Long.MAX_VALUE) {
                return l;
            }
            return l / batchSize;
        }

        @Override
        public int characteristics() {
            int c = NONNULL;
            c |= spliterator.hasCharacteristics(ORDERED) ? ORDERED : 0;
            c |= spliterator.hasCharacteristics(SIZED) ? SIZED : 0;
            return c;
        }
    }
}
