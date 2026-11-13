package edu.bean;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DayTest {

    @Test
    public void basic() {
        Day day = new Day("thursday", LocalDate.now());
        assertNotNull(day);
    }
}
