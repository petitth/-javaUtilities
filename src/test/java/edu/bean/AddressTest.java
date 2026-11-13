package edu.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressTest {

    @Test
    public void basic() {
        Address address = new Address();
        address.setCity("Gosselies");
        address.setCountry("Belgium");
        address.setStreetNo("1");
        address.setStreetName("avenue des Etats-Unis");
        address.setPostCode("6041");
        assertNotNull(address);
        assertEquals("Gosselies", address.getCity());
        assertEquals("Belgium", address.getCountry());
        assertEquals("1", address.getStreetNo());
        assertEquals("avenue des Etats-Unis", address.getStreetName());
        assertEquals("6041", address.getPostCode());
    }
}