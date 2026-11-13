package edu.string;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecimalFormatTest {

    @Test
    public void basic() {
        DecimalFormat df = new DecimalFormat("000");
        assertEquals("001", df.format(1).toString());
        assertEquals("010", df.format(10).toString());
        assertEquals("100", df.format(100).toString());
    }

    @Test
    public void hideZeros() {
        DecimalFormat df = new DecimalFormat("##0");
        assertEquals("1", df.format(1).toString());
        assertEquals("10", df.format(10).toString());
        assertEquals("100", df.format(100).toString());

        df = new DecimalFormat("###");
        assertEquals("0", df.format(0).toString());
        assertEquals("1", df.format(1).toString());
        assertEquals("10", df.format(10).toString());
        assertEquals("100", df.format(100).toString());
    }
}
