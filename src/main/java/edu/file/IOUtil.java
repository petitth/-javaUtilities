package edu.file;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class IOUtil {

    private IOUtil() { }

    /**
     * Convert an inputStream to a string.
     * @param inputStream inputStream
     * @return a string
     */
    public static String getString(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(joining("\n"))
                .trim();
    }

    /**
     * Convert an inputStream to List<String>.
     * @param inputStream inputStream
     * @return List<String>
     */
    public static List<String> getStringList(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList());
    }

    /**
     * Convert a string to an inputStream
     * @param string a string
     * @return an inputStream
     */
    public static InputStream getInputStream(String string) {
        return new ByteArrayInputStream(string.getBytes());
    }

    /**
     * Get InputStream from file path
     * @param filePath file path
     * @return InputStream
     * @throws IOException any exception
     */
    public static InputStream getInputStreamFromPath(String filePath) throws IOException {
        return new FileInputStream(new File(filePath));
    }

    /**
     * Get InputStream from byte array.
     * @param content byte array
     * @return InputStream
     */
    public static InputStream getInputStream(byte[] content) {
        return new ByteArrayInputStream(content);
    }

    /**
     * Get byte array from input stream.
     * @param inputStream input stream
     * @return a byte array
     * @throws IOException any exception
     */
    public static byte[] getByteArray(InputStream inputStream) throws IOException {
        byte[] byteArray = new byte[inputStream.available()];
        inputStream.read(byteArray,0, byteArray.length);
        return byteArray;
    }

    /**
     * Convert a string to a byte array
     * @param str string
     * @return a byte array
     */
    public static byte[] getByteArray(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Convert a byte array to a string
     * @param content byte array
     * @return a string
     */
    public static String getString(byte[] content) {
        return new String(content);
    }

}
