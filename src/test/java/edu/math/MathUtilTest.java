package edu.math;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathUtilTest {

    @Test
    public void fibonacci() {
        assertEquals(1, MathUtil.fibonacci(1));
        assertEquals(1, MathUtil.fibonacci(2));
        assertEquals(2, MathUtil.fibonacci(3));
        assertEquals(3, MathUtil.fibonacci(4));
        assertEquals(5, MathUtil.fibonacci(5));
        assertEquals(8, MathUtil.fibonacci(6));
        assertEquals(13, MathUtil.fibonacci(7));
        assertEquals(21, MathUtil.fibonacci(8));
    }

    @Test
    public void factorial() {
        assertEquals(1, MathUtil.factorial(0));
        assertEquals(1, MathUtil.factorial(1));
        assertEquals(2, MathUtil.factorial(2));
        assertEquals(6, MathUtil.factorial(3));
        assertEquals(24, MathUtil.factorial(4));
        assertEquals(120, MathUtil.factorial(5));
        assertEquals(720, MathUtil.factorial(6));
        assertEquals(5040, MathUtil.factorial(7));
        assertEquals(40320, MathUtil.factorial(8));
    }

    @Test
    public void byteToHex() {
        assertEquals("68", MathUtil.byteToHex((byte)104));
        assertEquals("65", MathUtil.byteToHex((byte)101));
        assertEquals("6C", MathUtil.byteToHex((byte)108));
        assertEquals("6F", MathUtil.byteToHex((byte)111));
    }

    @Test
    public void integerToBinary() {
        assertEquals("0", MathUtil.integerToBinary(0));
        assertEquals("1", MathUtil.integerToBinary(1));
        assertEquals("10", MathUtil.integerToBinary(2));
        assertEquals("11", MathUtil.integerToBinary(3));
        assertEquals("100", MathUtil.integerToBinary(4));
        assertEquals("101", MathUtil.integerToBinary(5));
        assertEquals("110", MathUtil.integerToBinary(6));
        assertEquals("111", MathUtil.integerToBinary(7));
        assertEquals("1000", MathUtil.integerToBinary(8));
        assertEquals("1001", MathUtil.integerToBinary(9));
        assertEquals("1010", MathUtil.integerToBinary(10));
        assertEquals("1011", MathUtil.integerToBinary(11));
        assertEquals("1100", MathUtil.integerToBinary(12));
    }

    @Test
    public void integerToHex() {
        assertEquals("0", MathUtil.integerToHex(0));
        assertEquals("1", MathUtil.integerToHex(1));
        assertEquals("2", MathUtil.integerToHex(2));
        assertEquals("3", MathUtil.integerToHex(3));
        assertEquals("4", MathUtil.integerToHex(4));
        assertEquals("5", MathUtil.integerToHex(5));

        assertEquals("A", MathUtil.integerToHex(10));
        assertEquals("B", MathUtil.integerToHex(11));
        assertEquals("C", MathUtil.integerToHex(12));
        assertEquals("D", MathUtil.integerToHex(13));
        assertEquals("E", MathUtil.integerToHex(14));
        assertEquals("F", MathUtil.integerToHex(15));
        assertEquals("10", MathUtil.integerToHex(16));
        assertEquals("11", MathUtil.integerToHex(17));
    }

    @Test
    public void roundFailing() {
        try {
            MathUtil.round(1, -1);
            fail("it cannot work");
        } catch(IllegalArgumentException ex) {
            assertNotNull(ex);
        }
    }

    @Test
    public void round() {
        assertEquals(2.87, MathUtil.round(2.866, 2));
        assertEquals(2.749, MathUtil.round(2.7488, 3));
        assertEquals(5.56, MathUtil.round(5.555, 2));
        assertEquals(7.55, MathUtil.round(7.554, 2));
    }

}
