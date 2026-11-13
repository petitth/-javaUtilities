package edu.apache.commonsLang3;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RansomStringUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(RansomStringUtilsTest.class);

    @Test
    public void randomAlphanumeric() {
        String randomString = RandomStringUtils.randomAlphanumeric(10);
        LOG.debug("random alphanumeric string : {} (size = {})", randomString, randomString.length());
        assertNotNull(randomString);
        assertEquals(10, randomString.length());
    }

    @Test
    public void randomAlphabetic() {
        String randomString = RandomStringUtils.randomAlphabetic(10);
        LOG.debug("random alphabetic string : {} (size = {})", randomString, randomString.length());
        assertNotNull(randomString);
        assertEquals(10, randomString.length());
    }

    @Test
    public void randomNumeric() {
        String randomString = RandomStringUtils.randomNumeric(10);
        LOG.debug("random numeric string : {} (size = {})", randomString, randomString.length());
        assertNotNull(randomString);
        assertEquals(10, randomString.length());
    }

}
