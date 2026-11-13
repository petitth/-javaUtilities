package edu.util;

import edu.bean.Employee;
import edu.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    private TestUtil() { }

    public static List<Person> getPersonList() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("Maria", "Anders"));
        list.add(new Person("Victoria", "Ashworth"));
        list.add(new Person("Christina", "Berglung"));
        list.add(new Person("Frederique", "Citeaux"));
        list.add(new Person("Thomas", "Hardy"));
        list.add(new Person("Hanna", "Moos"));
        list.add(new Person("Laurence", "Lebihan"));
        list.add(new Person("Elisabeth", "Lincoln"));
        list.add(new Person("Antonio", "Moreno"));
        list.add(new Person("Patricio", "Simpson"));
        list.add(new Person("Martaen", "Sommer"));
        list.add(new Person("Ana", "Trujillo"));
        return list;
    }

    public static List<Employee> getEmployeeList() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1L, "Maria", "Anders", "Germany",4000d));
        list.add(new Employee(2L, "Victoria", "Ashworth", "UK",5000d));
        list.add(new Employee(3L, "Christina", "Berglung", "Sweden",3000d));
        list.add(new Employee(4L, "Frederique", "Citeaux", "France",2500d));
        list.add(new Employee(5L, "Thomas", "Hardy", "UK",3000d));
        list.add(new Employee(6L, "Hanna", "Moos", "Germany",3500d));
        list.add(new Employee(7L, "Laurence", "Lebihan", "France",4000d));
        list.add(new Employee(8L, "Elisabeth", "Lincoln", "Canada",2000d));
        list.add(new Employee(9L, "Antonio", "Moreno", "Mexico",1900d));
        list.add(new Employee(10L, "Patricio", "Simpson", "Argentina",2600d));
        list.add(new Employee(11L, "Martaen", "Sommer", "Spain",3000d));
        list.add(new Employee(12L, "Ana", "Trujillo", "Mexico",6000d));
        return list;
    }

}