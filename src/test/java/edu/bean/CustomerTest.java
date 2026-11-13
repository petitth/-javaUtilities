package edu.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerTest {

    @Test
    public void basic() {
        Customer customer = new Customer();
        customer.setCustomerId("ALFKI");
        customer.setCompanyName("Alfreds Futterkiste");
        customer.setContactName("Maria Anders");
        customer.setContactTitle("Sales Representative");
        customer.setAddress("Obere Str. 57");
        customer.setCity("Berlin");
        customer.setPostalCode("12209");
        customer.setCountry("Germany");
        customer.setPhone("030-0074321");
        customer.setFax("030-0076545");
        assertNotNull(customer);
        assertEquals("ALFKI", customer.getCustomerId());
        assertEquals("Alfreds Futterkiste", customer.getCompanyName());
        assertEquals("Maria Anders", customer.getContactName());
        assertEquals("Sales Representative", customer.getContactTitle());
        assertEquals("Obere Str. 57", customer.getAddress());
        assertEquals("Berlin", customer.getCity());
        assertEquals("12209", customer.getPostalCode());
        assertEquals("Germany", customer.getCountry());
        assertEquals("030-0074321", customer.getPhone());
        assertEquals("030-0076545", customer.getFax());
    }
}
