package se.cygni.java8.golf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.cygni.java8.golf.Golfer;

import java.util.ArrayList;
import java.util.List;

import static se.cygni.java8.golf.Country.*;
import static se.cygni.java8.golf.Gender.FEMALE;
import static se.cygni.java8.golf.Gender.MALE;

public class TestUtil {

    public static  List<Golfer> createGolfers() {
        List<Golfer> golfers = new ArrayList<Golfer>();
        golfers.add(new Golfer("Annika", -1, FEMALE, SE, 33940));
        golfers.add(new Golfer("Nils", 21, MALE, SE, 55));
        golfers.add(new Golfer("Lotta", 0, FEMALE, SE, 3449));
        golfers.add(new Golfer("Bo", 11, MALE, DK, 929));
        golfers.add(new Golfer("Scott", 0, MALE, US, 922));
        golfers.add(new Golfer("Marit", 23, FEMALE, NO, 212));
        golfers.add(new Golfer("Preben", 0, MALE, DK, 7002));
        golfers.add(new Golfer("Tiger", -4, MALE, US, 33940));
        golfers.add(new Golfer("Kurt", 33, MALE, NO, 2));
        golfers.add(new Golfer("Henrik", -4, MALE, SE, 34960));
        golfers.add(new Golfer("John", 7, MALE, NO, 834));
        golfers.add(new Golfer("Bettan", 6, FEMALE, SE, 745));
        golfers.add(new Golfer("Anna", 17, FEMALE, DK, 64));
        golfers.add(new Golfer("Bengt", 54, MALE, SE, -8000));
        golfers.add(new Golfer("Sanne", 48, FEMALE, DK, 1));
        golfers.add(new Golfer("Arne", 29, MALE, SE, 305));
        return golfers;
    }
    private final static ObjectMapper objectMapper = new ObjectMapper();
    public static void prettyPrint(Object obj) {
        try {
            String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            System.out.println(s);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
