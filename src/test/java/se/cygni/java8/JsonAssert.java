package se.cygni.java8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;

public class JsonAssert {
    private final static ObjectMapper objectMapper;

    static {
        ObjectMapper m = new ObjectMapper();
        m.enableDefaultTyping();
        m.findAndRegisterModules();
        m.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper = m;
    }

    public static void assertJsonEquals(Object expected, Object actual) throws JsonProcessingException {
        assertJsonEquals(null, expected, actual);
    }

    public static void assertJsonEquals(String message, Object expected, Object actual) throws JsonProcessingException {
        Assert.assertEquals(
                message,
                objectMapper.writeValueAsString(expected),
                objectMapper.writeValueAsString(actual)
        );
    }
}
