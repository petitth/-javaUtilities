package edu.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XPathExceptionTest {

    @Test
    public void basic() {
        XPathException ex = new XPathException("an xpath error occurred");
        assertNotNull(ex);
        assertEquals("an xpath error occurred", ex.getMessage());
    }
}
