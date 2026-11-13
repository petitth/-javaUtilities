package edu.string;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StringUtilTest {

    @Test
    public void encodeAndDecodeBase64() {
        String inputString = "mylogin:mypwd";
        String encoded = StringUtil.getBase64String(inputString);
        assertNotNull(encoded);
        assertEquals(inputString, StringUtil.getDecodedString(encoded));
    }

    @Test
    public void repeat() {
        assertEquals("abcabcabc", StringUtil.repeat("abc",3));
    }
}
