package se.cygni.java8.golf;

public enum Level {
    PRO, SINGLE, ADVANCED, INTERMEDIATE, BEGINNER;

    public static Level getLevel(Golfer golfer) {
        int hcp = golfer.getHcp();
        if (hcp < 2) {
            return PRO;
        }
        else if (hcp < 10) {
            return SINGLE;
        }
        else if (hcp < 18) {
            return ADVANCED;
        }
        else if (hcp < 36) {
            return INTERMEDIATE;
        }
        else {
            return BEGINNER;
        }
    }
}
