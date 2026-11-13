package edu.bean;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DummyTest {

    @Test
    public void emptyConstructor() {
        Date date = new Date();
        Dummy dummy = new Dummy();
        dummy.setId("id1234");
        dummy.setDate(date);
        assertNotNull(dummy.getId());
        assertNotNull(dummy.getDate());
        assertEquals("id1234", dummy.getId());
        assertEquals(date, dummy.getDate());
    }

    @Test
    public void constructorWithParams() {
        Date date = new Date();
        Dummy dummy = new Dummy("id1234", date);
        assertNotNull(dummy.getId());
        assertNotNull(dummy.getDate());
        assertEquals("id1234", dummy.getId());
        assertEquals(date, dummy.getDate());
    }
}
