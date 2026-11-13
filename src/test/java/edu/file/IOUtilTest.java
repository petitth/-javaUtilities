package edu.file;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IOUtilTest {

    @Test
    public void convertToAndFromInputStream() {
        String message = "hello world";
        InputStream inputStream = IOUtil.getInputStream(message);
        assertNotNull(inputStream);
        String result = IOUtil.getString(inputStream);
        assertNotNull(result);
        assertEquals(message, result);
    }

    @Test
    public void getByteArrayFromInputStream() throws IOException {
        String input = "hello world";
        InputStream inputStream = IOUtil.getInputStream(input);
        byte[] bytes = IOUtil.getByteArray(inputStream);
        assertEquals(input, new String(bytes));
    }

    @Test
    public void getInputStreamFromByteArray() {
        byte[] byteArray = IOUtil.getByteArray("hello world");
        InputStream inputStream = IOUtil.getInputStream(byteArray);
        String result = IOUtil.getString(inputStream);
        assertEquals("hello world", result);
    }

    @Test
    public void getByteArrayFromString() throws Exception {
        byte[] result = IOUtil.getByteArray("hello world");
        assertNotNull(result);
    }

    @Test
    public void getStringFromByteArray() throws Exception {
        byte[] byteArray = "hello world".getBytes();
        String msg = IOUtil.getString(byteArray);
        assertEquals("hello world", msg);
    }

    @Test
    public void getStringListFromByteArray() throws Exception {
        InputStream inputStream = IOUtil.getInputStream("hello\nworld");
        List<String> list = IOUtil.getStringList(inputStream);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("hello", list.get(0));
        assertEquals("world", list.get(1));
    }

    @Test
    public void getInputStreamFromPath() throws IOException {
        Path path = Paths.get("./src/test/resources/person.xml");
        String expected = new String(Files.readAllBytes(path));

        InputStream inputStream = IOUtil.getInputStreamFromPath("./src/test/resources/person.xml");
        String fileContent = IOUtil.getString(inputStream);
        assertEquals(expected, fileContent);
    }

}
