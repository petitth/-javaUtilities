package edu.string;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class StringTest {

    private static final Logger LOG = LoggerFactory.getLogger(StringTest.class);

    @Test
    public void stringFormat() {
        int nbX = 10;
        int nbY = 8;
        String name = "Bob";

        LOG.info(String.format("%d + %d = %d",nbX, nbY, nbX+nbY));
        LOG.info(String.format("Hello %s", name));

        assertEquals("10 + 8 = 18", String.format("%d + %d = %d",nbX, nbY, nbX+nbY));
        assertEquals("Hello Bob", String.format("Hello %s", name));
    }

    @Test
    public void substring() {
        String xString = "hello world";

        assertEquals("hello", xString.substring(0, 5));
        assertEquals("hell", xString.substring(0, 4));

        assertEquals("hello", xString.substring(0, "hello".length()));
        assertEquals("world", xString.substring("hello".length() +1, xString.length()));
    }

    @Test
    public void join() {
        List<String> list = Arrays.asList("red", "green", "blue");
        String result = String.join("\n", list);
        assertEquals("red\ngreen\nblue", result);

        String[] array = new String[] { String.join(",", list)};
        assertEquals(1, array.length);
        assertEquals("red,green,blue", array[0]);
    }

    @Test
    public void referencing() {
        // string constant pool reuses reference of s1 for s2, since it has same value
        String s1 = "foobar";
        String s2 = "foobar";
        assertTrue(s1 == s2, "s1 == s2");

        // 'new' means creating a new reference every time, in heap memory
        String s3 = new String("foobar");
        String s4 = new String("foobar");
        assertFalse(s3 == s4, "s3 != s4");
        // remark : every usage of 'new' will a reference in the heap memory
        //          + a reference in string constant pool [if it does not exist already]
    }

    @Test
    public void referencingB() {
        String s1 = new String("foo");
        String s2 = "foo";
        assertFalse(s1 == s2, "both have different references");
        assertTrue(s1.equals(s2), "both have same content");
    }

    @Test
    public void stringsAreImmutable() {
        String s1 = "foo";
        int hashA = s1.hashCode();

        // a new reference is created in constant pool
        s1 = s1 + "bar";
        int hashB = s1.hashCode();
        assertFalse(hashA == hashB, "strings do not have the same reference");
    }

    @Test
    public void stringsAreImmutableB() {
        // StringBuilder will keep same reference
        StringBuilder sb = new StringBuilder();
        sb.append("foo");
        int hashA = sb.hashCode();

        sb.append("bar");
        int hashB = sb.hashCode();
        assertEquals(hashA, hashB);
    }

}