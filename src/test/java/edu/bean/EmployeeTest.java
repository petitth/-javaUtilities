package edu.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void emptyNoArgConstructor() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setCountry("Belgium");
        employee.setSalary(5000.0);
        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals(5000.0, employee.getSalary());
        assertEquals("Belgium", employee.getCountry());
    }

    @Test
    public void allArgsConstructor() {
        Employee employee = new Employee(1L, "Bob", "Razovsky", 8000.0);
        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals("Bob", employee.getFirstName());
        assertEquals("Razovsky", employee.getLastName());
        assertEquals(8000.0, employee.getSalary());
    }

    @Test
    public void compare() {
        Employee employeeA = new Employee(1L, "Bob", "Razovsky", 8000.0);
        Employee employeeB = new Employee(2L, "John", "Smith", 8000.0);
        Employee employeeC = new Employee(3L, "Bob", "Razovsky", 8000.0);
        Employee employeeD = new Employee(4L, "Bob", "Smith", 8000.0);
        assertFalse(employeeA.equals(employeeB));
        assertTrue(employeeA.equals(employeeA));
        assertTrue(employeeA.equals(employeeC));
        assertFalse(employeeA.equals(employeeD));
        assertFalse(employeeA.hashCode() == employeeB.hashCode());
    }

}
