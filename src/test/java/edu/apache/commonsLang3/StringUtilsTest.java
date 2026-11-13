package edu.apache.commonsLang3;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilsTest {

    @Test
    public void constants() {
        assertTrue(" ".equals(StringUtils.SPACE) );
        assertTrue("".equals(StringUtils.EMPTY));
        assertTrue("\n".equals(StringUtils.LF));
        assertTrue("\r".equals(StringUtils.CR));
        assertTrue(-1 == StringUtils.INDEX_NOT_FOUND);
    }

    @Test
    public void center() {
        String str = "foobar";
        assertEquals("       foobar       ", StringUtils.center(str, 20));
    }

    @Test
    public void stripAccents() {
        String str = "foo éàè bar êiî foo éêè bar";
        assertEquals("foo eae bar eii foo eee bar", StringUtils.stripAccents(str));
    }

    /**
     * Remove last character
     */
    @Test
    public void chop() {
        assertEquals("fooba", StringUtils.chop("foobar"));
        assertEquals("fo", StringUtils.chop("foo"));
        assertEquals("ba", StringUtils.chop("bar"));
        assertEquals("", StringUtils.chop(""));
    }

    @Test
    public void repeat() {
        assertEquals("foobarfoobarfoobar", StringUtils.repeat("foobar",3));
    }

    @Test
    public void replaceIgnoreCase() {
        assertEquals("FoBLAaR", StringUtils.replaceIgnoreCase("FoObaR","OB", "BLA"));
    }

    @Test
    public void reverse() {
        assertEquals("raboof", StringUtils.reverse("foobar"));
    }

    @Test
    public void strip() {
        // remove beginning and ending white spaces
        assertEquals("foo bar", StringUtils.strip(" foo bar "));
        // remove a string from beginning and ending
        assertEquals("bar", StringUtils.strip("foobarfoo", "foo"));
    }

    @Test
    public void truncate() {
        assertEquals("foob", StringUtils.truncate("foobar", 4));
    }
}
