package edu.string;

import java.util.Base64;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class StringUtil {

    private StringUtil() { }

    /**
     * Get a base 64 encoded string
     * @param string input string
     * @return base 64 encoded string
     */
    public static String getBase64String(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

    /**
     * Decode a base 64 string.
     * @param base64String base 64 encoded string
     * @return decoded string
     */
    public static String getDecodedString(String base64String) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        return new String(decodedBytes);
    }

    /**
     * Repeat a string.
     * This works with Java 8.
     * @param str string to repeat
     * @param times how many times the string will be repeated
     * @return a repeated string
     */
    public static String repeat(String str, int times) {
        return Stream.generate(() -> str).limit(times).collect(joining());
    }
}
