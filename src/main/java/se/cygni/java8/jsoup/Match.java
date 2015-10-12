package se.cygni.java8.jsoup;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.Objects;
import java.util.function.Predicate;

public class Match {
    public static Predicate<Node> tag(String tagName) {
        return n -> {
            if (n instanceof Element) {
                Element e = (Element) n;
                return e.tagName().equalsIgnoreCase(tagName);
            }
            return false;
        };
    }

    public static Predicate<Node> parent(String tagName) {
        return n -> tag(tagName).test(n.parent());
    }

    public static Predicate<? super Node> attr(String attr, String expectedValue) {
        return n -> Objects.equals(expectedValue, n.attr(attr));
    }
}
