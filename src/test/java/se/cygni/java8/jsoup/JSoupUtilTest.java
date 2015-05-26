package se.cygni.java8.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Stream;

public class JSoupUtilTest {

    public static final int ITERATIONS = 1000;
    private Document document;

    @Before
    public void before() throws Exception {
        document = Jsoup.parse(getResource("jsoup.org.html"), "UTF-8");
    }

    private File getResource(String name) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(name).toURI()).toFile();
    }

    @Test
    public void testBenchmark() throws Exception {
        document = Jsoup.parse(getResource("large.html"), "UTF-8");
//        Function<Stream<Node>, Long> func =
//                s -> s.filter(Match.tag("font").and(Match.parent("pre"))).findAny().map(n -> 1L).orElse(0L);

        Function<Stream<Node>, Long> func =
                s -> s.filter(Match.attr("width", "174")).findAny().map(n -> 1L).orElse(0L);

        benchmark("seq iterator1Stream", () -> func.apply(JSoupUtil.iterator1Stream(document)), ITERATIONS);
        benchmark("seq iterator2Stream", () -> func.apply(JSoupUtil.iterator2Stream(document)), ITERATIONS);
        benchmark("seq spliteratorStream", () -> func.apply(JSoupUtil.spliteratorStream(document)), ITERATIONS);
        benchmark("seq streamStream", () -> func.apply(JSoupUtil.streamStream(document)), ITERATIONS);
        benchmark("par iterator1Stream", () -> func.apply(JSoupUtil.iterator1Stream(document).parallel()), ITERATIONS);
        benchmark("par iterator1Stream", () -> func.apply(JSoupUtil.iterator2Stream(document).parallel()), ITERATIONS);
        benchmark("par spliteratorStream", () -> func.apply(JSoupUtil.spliteratorStream(document).parallel()), ITERATIONS);
        benchmark("par streamStream", () -> func.apply(JSoupUtil.streamStream(document).parallel()), ITERATIONS);

        benchmark("computeLength", () -> computeLength("hello"), ITERATIONS);
    }

    private static Long computeLength(String s) {
        return Long.valueOf(s.length());
    }

    private void benchmark(String name, Callable<Long> target, int iterations) throws Exception {
        long total = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            total += target.call();
        }
        long dur = System.currentTimeMillis() - start;
        System.out.printf("%-20s: %.3f ms/iter       %d%n", name, dur / (double) iterations, total);

    }

    @Test
    public void testSpliteratorStream() throws Exception {
        JSoupUtil.spliteratorStream(document)
                .peek(s -> {})
                .filter(Match.tag("a").and(Match.parent("p")))
                .map(n -> (Element) n)
                .map(e -> e.attr("href"))
                .forEach(System.out::println);
    }

    @Test
    public void testStreamStream() throws Exception {
        JSoupUtil.streamStream(document)
                .filter(Match.tag("a").and(Match.parent("p")))
                .map(n -> (Element) n)
                .map(e -> e.attr("href"))
                .forEach(System.out::println);
    }

}