package se.cygni.java8.exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

public class Ex1MapAndSumTest {

    private List<Golfer> golfers;

    public static List<Golfer> createGolfers() {
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

    @Before
    public void before() throws Exception {
        golfers = createGolfers();
    }

    @Test
    public void testFindFirstGolfer() throws Exception {
        Optional<Golfer> golfer = Ex1MapAndSum.findFirstGolfer(golfers);
        Assert.assertEquals("Annika", golfer.map(Golfer::getName).orElse(null));
    }

    @Test
    public void testFindFirstGolferHcp() throws Exception {
        Optional<Integer> hcp = Ex1MapAndSum.findFirstGolferHcp(createGolfers());
        Assert.assertEquals(-1, (int) hcp.orElse(Integer.MAX_VALUE));
    }

    @Test
    public void testFindNameOfFirstAboveHcp30() throws Exception {
        Optional<String> name = Ex1MapAndSum.findNameOfFirstAboveHcp30(createGolfers());
        Assert.assertEquals("Kurt", name.orElse(null));


    }

    @Test
    public void testSumOfMoney() throws Exception {
        int sum = 0;
        for (Golfer golfer : golfers) {
            sum += golfer.getMoney();
        }
        Assert.assertEquals(sum, Ex1MapAndSum.sumOfMoney(golfers));
    }

    @Test
    public void testAverageHcp() throws Exception {
        int sum = 0;
        for (Golfer golfer : golfers) {
            sum += golfer.getHcp();
        }
        Assert.assertEquals(sum/golfers.size(), Ex1MapAndSum.averageHcp(golfers));
    }

    @Test
    public void testSumOfLettersInNames() throws Exception {
        int sum = 0;
        for (Golfer golfer : golfers) {
            sum += golfer.getName().length();
        }
        Assert.assertEquals(sum, Ex1MapAndSum.sumOfLettersInNames(golfers));
    }
}