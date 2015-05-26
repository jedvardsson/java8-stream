package se.cygni.java8;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsFirst;
import static java.util.Comparator.reverseOrder;

public class J7_8Test {


    private List<Golfer> golfers;


    @Before
    public void before() throws Exception {
        golfers = new ArrayList<>();
        golfers.add(new Golfer("Tiger", -3, 10001));
        golfers.add(new Golfer("Johan", 43, 300));
        golfers.add(new Golfer("Fredrik", 54, -10));
        golfers.add(new Golfer("Daniel", 20, 300));
        golfers.add(new Golfer("Mats", 8, 0));
        golfers.add(new Golfer("Niklas", 18, 2345));
        golfers.add(new Golfer(null, 12, 2345));
    }

    @Test
    public void testList7() throws Exception {
        List<String> names = new ArrayList<>();
        for (Golfer golfer : golfers) {
            names.add(golfer.getName());
        }
        System.out.println(names);
    }

    @Test
    public void testList8() throws Exception {
        List<String> names = golfers.parallelStream().unordered()
                .map(g -> String.valueOf(g.getName()))
                .map(s -> s.toUpperCase())
                .collect(Collectors.toList());
        System.out.println(names);
    }

    @Test
    public void testLevel7() throws Exception {
        Map<Level, List<Golfer>> byLevel = new HashMap<>();
        for (Golfer golfer : golfers) {
            Level level = Level.getLevel(golfer);
            List<Golfer> list = byLevel.get(level);
            if (list == null) {
                list = new ArrayList<>();
                byLevel.put(level, list);
            }
            list.add(golfer);
        }

        for (Map.Entry<Level, List<Golfer>> levelListEntry : byLevel.entrySet()) {
            System.out.println(levelListEntry.getKey());
            for (Golfer golfer : levelListEntry.getValue()) {
                System.out.println("--> " + golfer.getName());
            }
        }
    }

    @Test
    public void testLevel8() throws Exception {
        Supplier<Map<Level, List<Golfer>>> constructor1 = HashMap::new;
        Supplier<Map<Level, List<Golfer>>> constructor2 = () -> new HashMap<>();

        Map<Level, List<Golfer>> byLevel = golfers.stream()
                .collect(Collectors.groupingBy(Level::getLevel));

        byLevel.forEach((level, golfers) -> {
            System.out.println(level);
            golfers.forEach(golfer -> System.out.println("--> " + golfer.getName()));
        });
    }

    @Test
    public void testSort7() throws Exception {
        ArrayList<Golfer> sorted = new ArrayList<>(golfers);
        Collections.sort(sorted, new Comparator<Golfer>() {
            @Override
            public int compare(Golfer o1, Golfer o2) {
                return o1.getMoney() - o2.getMoney();
            }
        });
        for (Golfer golfer : sorted) {
            System.out.println(golfer);
        }
    }

    @Test
    public void testSort8a() throws Exception {
        List<Golfer> sorted = golfers.stream().sorted((g1, g2) -> g1.getMoney() - g2.getMoney()).collect(Collectors.toList());
        sorted.forEach(System.out::println);
    }

    @Test
    public void testSort8b() throws Exception {
        Comparator<Golfer> comparator = Arrays.asList(
                comparing(Golfer::getMoney).reversed(),
                comparing(Golfer::getName, nullsFirst(naturalOrder())),
                comparing(Golfer::getHcp).reversed())
                .stream().reduce(Comparator::thenComparing).get();

        comparing(Golfer::getMoney).reversed()
                .thenComparing(comparing(Golfer::getName, nullsFirst(naturalOrder())))
                .thenComparing(comparing(Golfer::getHcp).reversed());

        List<Golfer> sorted = golfers.stream()
                .sorted(comparator).collect(Collectors.toList());
        sorted.forEach(System.out::println);
    }

    private static class SortAttr {
        private String key;
        private boolean desc;
        private boolean nullable;

        public SortAttr(String key, boolean desc) {
            this.key = key;
            this.desc = desc;
        }

        private SortAttr(String key, boolean desc, boolean nullable) {
            this.key = key;
            this.desc = desc;
            this.nullable = nullable;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean isDesc() {
            return desc;
        }

        public void setDesc(boolean desc) {
            this.desc = desc;
        }

        public boolean isNullable() {
            return nullable;
        }

        public void setNullable(boolean nullable) {
            this.nullable = nullable;
        }
    }

    @Test
    public void testSort8c() throws Exception {
        Comparator<Golfer> comparator = (a, b) -> 0;
        ArrayList<SortAttr> sortAttrs = new ArrayList<SortAttr>();
        sortAttrs.add(new SortAttr("money", true));
        sortAttrs.add(new SortAttr("name", false, true));

        nullsFirst(naturalOrder());

        for (SortAttr sortAttr : sortAttrs) {
            Function<Golfer, ?> keyMapper;
            switch (sortAttr.getKey()) {
                case "name":
                    keyMapper = Golfer::getName;
                    break;
                case "money":
                    keyMapper = Golfer::getMoney;
                    break;
                case "level":
                    keyMapper = Level::getLevel;
                    break;
                case "hcp":
                    keyMapper = Golfer::getHcp;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            Comparator c = sortAttr.isDesc() ? reverseOrder() : naturalOrder();
            c = sortAttr.isNullable() ? nullsFirst(c) : c;
            comparator = comparator.thenComparing(comparing(keyMapper, c));
        }
        List<Golfer> sorted = golfers.stream()
                .sorted(comparator).collect(Collectors.toList());
        sorted.forEach(System.out::println);
    }

    private static class SortAttr2<T>  {
        private Function<T, ?> keyMapper;
        private boolean desc;
        private boolean nullable;

        public SortAttr2(Function<T, ?> keyMapper, boolean desc) {
            this.keyMapper = keyMapper;
            this.desc = desc;
        }

        public SortAttr2(Function<T, ?> keyMapper, boolean desc, boolean nullable) {
            this.keyMapper = keyMapper;
            this.desc = desc;
            this.nullable = nullable;
        }

        public Function<T, ?> getKeyMapper() {
            return keyMapper;
        }

        public void setKeyMapper(Function<T, ?> keyMapper) {
            this.keyMapper = keyMapper;
        }

        public boolean isDesc() {
            return desc;
        }

        public void setDesc(boolean desc) {
            this.desc = desc;
        }

        public boolean isNullable() {
            return nullable;
        }

        public void setNullable(boolean nullable) {
            this.nullable = nullable;
        }
    }

    @Test
    public void testSort8d() throws Exception {
        Comparator<Golfer> comparator = (a, b) -> 0;
        ArrayList<SortAttr2<Golfer>> sortAttrs = new ArrayList<>();
        sortAttrs.add(new SortAttr2<>(Golfer::getMoney, true));
        sortAttrs.add(new SortAttr2<>(Golfer::getName, false, true));

        nullsFirst(naturalOrder());

        for (SortAttr2 sortAttr : sortAttrs) {
            Comparator c = sortAttr.isDesc() ? reverseOrder() : naturalOrder();
            c = sortAttr.isNullable() ? nullsFirst(c) : c;
            comparator = comparator.thenComparing(comparing(sortAttr.getKeyMapper(), c));
        }
        List<Golfer> sorted = golfers.stream()
                .sorted(comparator).collect(Collectors.toList());
        sorted.forEach(System.out::println);
    }

    public static enum Level {
        BEGINNER, ADVANCED, PRO;

        public static Level getLevel(Golfer golfer) {
            if (golfer.getHcp() < 2) {
                return PRO;
            } else if (golfer.getHcp() < 19) {
                return ADVANCED;
            } else {
                return BEGINNER;
            }
        }
    }

    public static class Golfer {
        private String name;
        private int hcp;
        private int money;

        public Golfer(String name, int hcp, int money) {
            this.name = name;
            this.hcp = hcp;
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHcp() {
            return hcp;
        }

        public void setHcp(int hcp) {
            this.hcp = hcp;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        @Override
        public String toString() {
            return "Golfer{" +
                    "name='" + name + '\'' +
                    ", hcp=" + hcp +
                    ", money=" + money +
                    '}';
        }
    }
}
