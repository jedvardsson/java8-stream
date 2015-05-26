package se.cygni.java8.exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.cygni.java8.golf.Golfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static se.cygni.java8.golf.Country.DK;
import static se.cygni.java8.golf.Country.NO;
import static se.cygni.java8.golf.Country.SE;
import static se.cygni.java8.golf.Country.US;
import static se.cygni.java8.golf.Gender.FEMALE;
import static se.cygni.java8.golf.Gender.MALE;

public class Ex1 {

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
}
