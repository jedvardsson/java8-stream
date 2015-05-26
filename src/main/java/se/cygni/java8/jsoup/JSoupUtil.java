package se.cygni.java8.jsoup;

import org.jsoup.nodes.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JSoupUtil {

    public static Stream<Node> streamStream(Node node) {
        return Stream.concat(Stream.of(node),
                node.childNodes().stream().map(n -> {
                    return streamStream(n);
                }).flatMap(Function.identity()));
    }

    public static Stream<Node> iterator1Stream(Node node) {
        Spliterator<Node> spliterator = Spliterators.spliteratorUnknownSize(iterator1(node), Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false);
    }

    public static Stream<Node> iterator2Stream(Node node) {
        Spliterator<Node> spliterator = Spliterators.spliteratorUnknownSize(iterator2(node), Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false);
    }

    public static Stream<Node> spliteratorStream(Node node) {
        return StreamSupport.stream(spliterator(node), false);
    }

    public static Iterator<Node> iterator1(Node node) {
        Deque<Iterator<Node>> iterators = new ArrayDeque<>();
        iterators.add(Stream.of(node).iterator());

        return new Iterator<Node>() {
            @Override
            public boolean hasNext() {
                while (!iterators.isEmpty() && !iterators.peek().hasNext()) {
                    iterators.pop();
                }
                return !iterators.isEmpty() && iterators.peek().hasNext();
            }

            @Override
            public Node next() {
                if (hasNext()) {
                    Node next = iterators.peek().next();
                    iterators.push(next.childNodes().iterator());
                    return next;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static Iterator<Node> iterator2(Node node) {
        Deque<Node> nodes = new ArrayDeque<>();
        nodes.add(node);

        return new Iterator<Node>() {
            @Override
            public boolean hasNext() {
                return !nodes.isEmpty();
            }

            @Override
            public Node next() {
                if (hasNext()) {
                    Node next = nodes.pop();
                    List<Node> childNodes = next.childNodes();
                    for (int i = 0; i < childNodes.size(); i++) {
                        nodes.push(childNodes.get(i));
                    }
                    return next;
                }
                throw new NoSuchElementException();
            }
        };
    }


    public static Spliterator<Node> spliterator(Node node) {
        Deque<Node> nodes = new ArrayDeque<>();
        nodes.add(node);
        return spliterator(nodes);
    }

    private static class NodeSpliterator implements Spliterator<Node> {
        public static final int THRESHOLD = 100;
        private final Deque<Node> nodes;

        public NodeSpliterator(Deque<Node> nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Node> action) {
            if (nodes.isEmpty()) {
                return false;
            }
            Node next = nodes.pop();
            List<Node> childNodes = next.childNodes();
            for (int i = 0; i < childNodes.size(); i++) {
                nodes.push(childNodes.get(i));
            }
            action.accept(next);
            return true;
        }

        @Override
        public Spliterator<Node> trySplit() {
            int size = nodes.size();
            if (size > THRESHOLD) {
                int halfSize = size / 2;
                ArrayDeque<Node> other = new ArrayDeque<>();
                for (int i = 0; i < halfSize; i++) {
                    other.addFirst(nodes.pop());
                }
                return new NodeSpliterator(other);
            }
            return null;
        }

        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }

        @Override
        public int characteristics() {
            return Spliterator.ORDERED;
        }
    }

    public static Spliterator<Node> spliterator(Deque<Node> nodes) {
        return new NodeSpliterator(nodes);
    }
}
