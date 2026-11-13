package edu.bean;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

    @Test
    public void emptyConstructor() {
        Person person = new Person();
        person.setFirstName("Bob");
        person.setLastName("Razovsky");
        assertEquals("Bob", person.getFirstName());
        assertEquals("Razovsky", person.getLastName());
        assertEquals("Bob Razovsky", person.toString());
    }

    @Test
    public void constructorWithParams() {
        Person person = new Person("Bob","Razovsky");
        assertEquals("Bob", person.getFirstName());
        assertEquals("Razovsky", person.getLastName());
        assertEquals("Bob Razovsky", person.toString());
    }

}
