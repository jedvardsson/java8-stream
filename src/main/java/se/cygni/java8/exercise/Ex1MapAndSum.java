package se.cygni.java8.exercise;

import se.cygni.java8.golf.Golfer;

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
        return golfers.stream().filter(g -> g.getHcp() > 30).findFirst().map(Golfer::getName);
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
