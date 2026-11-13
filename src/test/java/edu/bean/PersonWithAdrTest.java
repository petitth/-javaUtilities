package edu.bean;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PersonWithAdrTest {

    @Test
    public void basic() {
        PersonWithAdr person = new PersonWithAdr("John", "Smith");
        person.setAddressList(new ArrayList<>());
        assertNotNull(person);
        assertEquals("John", person.getFirstName());
        assertEquals("Smith", person.getLastName());
    }

    @Test
    public void compare() {
        PersonWithAdr personA = new PersonWithAdr("Bob", "Razovsky");
        PersonWithAdr personB = new PersonWithAdr("John", "Smith");
        assertFalse(personA.equals(personB));
        assertTrue(personA.equals(personA));
        assertFalse(personA.hashCode() == personB.hashCode());
        assertFalse(personA.equals(null));
        assertFalse(personA.equals(new Day()));
    }
}
