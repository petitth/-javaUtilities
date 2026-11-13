package edu.apache.commonsBeanUtils;

import edu.bean.Employee;
import edu.bean.Person;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BeanUtilsTest {

    @Test
    public void initBeans() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Person person = new Person();
        PropertyUtils.setProperty(person, "firstName", "John");
        PropertyUtils.setProperty(person, "lastName", "Smith");
        assertEquals("John", person.getFirstName());
        assertEquals("Smith", person.getLastName());
        assertEquals("John", PropertyUtils.getProperty(person, "firstName"));
        assertEquals("Smith", PropertyUtils.getProperty(person, "lastName"));
    }

    @Test
    public void copyProperties() throws InvocationTargetException, IllegalAccessException {
        Person person = new Person("John", "Smith");
        Employee employee = new Employee();
        BeanUtils.copyProperties(employee, person);
        assertEquals("John", employee.getFirstName());
        assertEquals("Smith", employee.getLastName());
        assertNull(employee.getId());
        assertNull(employee.getSalary());
        assertNull(employee.getCountry());
    }

}
