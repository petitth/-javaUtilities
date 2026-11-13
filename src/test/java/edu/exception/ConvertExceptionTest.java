package edu.exception;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConvertExceptionTest {

    @Test
    public void basic() {
        ConvertException ex = new ConvertException("an error occurred");
        assertNotNull(ex);
        assertEquals("an error occurred", ex.getMessage());
    }
}
