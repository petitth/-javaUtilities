package edu.string;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegexUtilTest {

    @Test
    public void isOnlyNumericTest() throws Exception {
        assertEquals(true, RegexUtil.isOnlyNumeric("123456"));
        assertEquals(true, RegexUtil.isOnlyNumeric("987456"));
        assertEquals(true, RegexUtil.isOnlyNumeric("55555555555555"));

        assertEquals(false, RegexUtil.isOnlyNumeric("123456a"));
        assertEquals(false, RegexUtil.isOnlyNumeric("e123456"));
        assertEquals(false, RegexUtil.isOnlyNumeric("123y456"));
        assertEquals(false, RegexUtil.isOnlyNumeric("abcdef"));

        assertEquals(false, RegexUtil.isOnlyNumeric("123 456"));
        assertEquals(false, RegexUtil.isOnlyNumeric("123_456"));
        assertEquals(false, RegexUtil.isOnlyNumeric("1234#56"));
        assertEquals(false, RegexUtil.isOnlyNumeric("1.23456"));
    }

    @Test
    public void isAlphaNumericOnlyTest() throws Exception {
        assertEquals(true, RegexUtil.isOnlyAlphaNumeric("123456"));
        assertEquals(true, RegexUtil.isOnlyAlphaNumeric("abcdef"));
        assertEquals(true, RegexUtil.isOnlyAlphaNumeric("abcd789"));
        assertEquals(true, RegexUtil.isOnlyAlphaNumeric("456azerty"));

        assertEquals(false, RegexUtil.isOnlyAlphaNumeric("123#456"));
        assertEquals(false, RegexUtil.isOnlyAlphaNumeric("123_456"));
        assertEquals(false, RegexUtil.isOnlyAlphaNumeric("bla.bla.bla"));
        assertEquals(false, RegexUtil.isOnlyAlphaNumeric("$omething"));
        assertEquals(false, RegexUtil.isOnlyAlphaNumeric("_anything"));
        assertEquals(false, RegexUtil.isOnlyAlphaNumeric("really_anything"));
    }

    @Test
    public void isAlphaNumericAndNumericOnlyTest() throws Exception {
        assertEquals(true, RegexUtil.isOnlyNumericAndAlphanumeric("a1"));
        assertEquals(true, RegexUtil.isOnlyNumericAndAlphanumeric("abcdef1"));
        assertEquals(true, RegexUtil.isOnlyNumericAndAlphanumeric("2abcdef"));
        assertEquals(true, RegexUtil.isOnlyNumericAndAlphanumeric("abc3def"));
        assertEquals(true, RegexUtil.isOnlyNumericAndAlphanumeric("4abc5def6"));

        assertEquals(true, RegexUtil.isOnlyNumericAndAlphanumeric("a123456"));
        assertEquals(true, RegexUtil.isOnlyNumericAndAlphanumeric("123456z"));
        assertEquals(true, RegexUtil.isOnlyNumericAndAlphanumeric("123e456"));
        assertEquals(true, RegexUtil.isOnlyNumericAndAlphanumeric("a123e456z"));


        assertEquals(false, RegexUtil.isOnlyNumericAndAlphanumeric("123456"));
        assertEquals(false, RegexUtil.isOnlyNumericAndAlphanumeric("abcdef"));
        assertEquals(false, RegexUtil.isOnlyNumericAndAlphanumeric("abcdef_"));
        assertEquals(false, RegexUtil.isOnlyNumericAndAlphanumeric("abc#def"));
        assertEquals(false, RegexUtil.isOnlyNumericAndAlphanumeric(".abcdef"));
        assertEquals(false, RegexUtil.isOnlyNumericAndAlphanumeric("abc#123"));
        assertEquals(false, RegexUtil.isOnlyNumericAndAlphanumeric("_abc123"));
    }

    @Test
    public void containsOnlyRepeatingStringOnlyTest() throws Exception {
        assertEquals(true, RegexUtil.containsOnlyRepeatingString("XXXXXXXXX", "X"));
        assertEquals(true, RegexUtil.containsOnlyRepeatingString("ZZZZ", "Z"));
        assertEquals(true, RegexUtil.containsOnlyRepeatingString("ABCABCABC", "ABC"));

        assertEquals(false, RegexUtil.containsOnlyRepeatingString("123456", "X"));
        assertEquals(false, RegexUtil.containsOnlyRepeatingString("AXXXXXXXX", "X"));
        assertEquals(false, RegexUtil.containsOnlyRepeatingString("AXXXXXXXXA", "X"));
        assertEquals(false, RegexUtil.containsOnlyRepeatingString("XXXXXXXXA", "X"));
        assertEquals(false, RegexUtil.containsOnlyRepeatingString("azerty", "X"));
    }

}