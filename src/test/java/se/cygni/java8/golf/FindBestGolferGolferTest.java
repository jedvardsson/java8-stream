package se.cygni.java8.golf;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class FindBestGolferGolferTest {

    private List<Golfer> golfers;

    @Before
    public void before() throws Exception {
        golfers = TestUtil.createGolfers();
    }

    @Test
    public void testBestUsingLambda() {
        Optional<Golfer> best = golfers.stream()
                .min((a, b) -> a.getHcp() - b.getHcp());
        System.out.println(best);
    }

    @Test
    public void testBestUsingComparator() {
        Comparator<Golfer> comparator =
                comparing(Golfer::getHcp)
                        .reversed()
                        .thenComparing(comparing(Golfer::getMoney))
                        .thenComparing(Golfer::getName);
        Optional<Golfer> best = golfers.stream()
                .max(comparator);
        System.out.println(best);
    }

    static class SortAttr {
        private final String key;
        private final boolean reversed;

        public SortAttr(String key) {
            this(key, false);
        }

        public SortAttr(String key, boolean reversed) {
            this.key = key;
            this.reversed = reversed;
        }

        public String getKey() {
            return key;
        }

        public boolean isReversed() {
            return reversed;
        }
    }

    @Test
    public void testSorting() {
        List<SortAttr> sortAttrs = new ArrayList<>();
        sortAttrs.add(new SortAttr("money", true));
        sortAttrs.add(new SortAttr("hcp"));
        sortAttrs.add(new SortAttr("name"));

        Comparator<Golfer> comparator = (g1, g2) -> 0;
        for (SortAttr sortAttr : sortAttrs) {
            Comparator<Golfer> c;
            switch (sortAttr.getKey()) {
                case "money":
                    c = Comparator.comparing(Golfer::getMoney);
                    break;
                case "hcp":
                    c = Comparator.comparing(Golfer::getHcp);
                    break;
                case "name":
                    c = Comparator.comparing(Golfer::getName);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown key: " + sortAttr.getKey());
            }
            if (sortAttr.isReversed()) {
                c = c.reversed();
            }
            comparator = comparator.thenComparing(c);
        }

        golfers.stream().sorted(comparator).forEach(System.out::println);
    }

    @Test
    public void testSortingStream() {
        List<SortAttr> sortAttrs = new ArrayList<>();
        sortAttrs.add(new SortAttr("money", true));
        sortAttrs.add(new SortAttr("hcp"));
        sortAttrs.add(new SortAttr("name"));

        Comparator<Golfer> comparator = sortAttrs.stream().map(sortAttr -> {
            Comparator<Golfer> c;
            switch (sortAttr.getKey()) {
                case "money":
                    c = Comparator.comparing(Golfer::getMoney);
                    break;
                case "hcp":
                    c = Comparator.comparing(Golfer::getHcp);
                    break;
                case "name":
                    c = Comparator.comparing(Golfer::getName);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown key: " + sortAttr.getKey());
            }
            if (sortAttr.isReversed()) {
                c = c.reversed();
            }
            return c;
        }).reduce((g1, g2) -> 0, Comparator::thenComparing);

        golfers.stream().sorted(comparator).forEach(System.out::println);
    }

    @Test
    public void testReduceVsCollect() {
        // Reduce removes pairs of elements
        // by replacing it with a new element.
        int totalMoney1 = golfers.stream()
                .mapToInt(Golfer::getMoney)
                .reduce(0, (a, b) -> a + b);

        int totalMoney2 = golfers.stream()
                .mapToInt(Golfer::getMoney)
                .reduce(0, Integer::sum);

        int totalMoney3 = golfers.stream().
                mapToInt(Golfer::getMoney)
                .sum();

        String s1 = golfers.stream()
                .map(Golfer::getName)
                .reduce("", String::concat);

        // Collect modifies existing value
        String s2 = golfers.stream()
                .map(Golfer::getName)
                .collect(StringBuilder::new,    // Supplier
                        StringBuilder::append,  // Accumulator
                        StringBuilder::append)  // Combiner
                .toString();
        String s3 = golfers.stream()
                .map(Golfer::getName)
                .filter(n -> n.toUpperCase().startsWith("T"))
                .collect(Collectors.joining(", "));

        List<String> namesOnT = golfers.stream()
                .map(Golfer::getName)
                .filter(n -> n.toUpperCase().startsWith("T"))
                .collect(Collectors.toList());
    }
}