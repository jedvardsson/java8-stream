package se.cygni.java8.exercise;

import se.cygni.java8.golf.Golfer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Ex1MapAndSum {

    public static Optional<Golfer> findFirstGolfer(List<Golfer> golfers) {
        return golfers.stream().findFirst();
    }

    public static Optional<Integer> findFirstGolferHcp(List<Golfer> golfers) {
        return golfers.stream().findFirst().map(Golfer::getHcp);
    }

    public static Optional<String> findNameOfFirstAboveHcp30(List<Golfer> golfers) {
        List<Object> collect = golfers.stream()
                .filter(g -> g.getHcp() > 30)
                .collect(
                        LinkedList::new,
                        (a, g) -> a.add(g),
                        (a, b) -> a.addAll(b));


//                .findFirst()
//                .map(Golfer::getName);
    }

    public static int sumOfMoney(List<Golfer> golfers) {
        return golfers.stream().mapToInt(Golfer::getMoney).sum();
    }

    public static int averageHcp(List<Golfer> golfers) {
        return (int) golfers.stream().mapToInt(Golfer::getHcp).summaryStatistics().getAverage();
    }

    public static int sumOfLettersInNames(List<Golfer> golfers) {
        return golfers.stream().map(Golfer::getName).mapToInt(String::length).sum();
    }

}
