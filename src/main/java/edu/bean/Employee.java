package edu.bean;

import java.util.Objects;

public class Employee extends Person {

    private Long id;
    private Double salary;
    private String country;

    public Employee() {
        this.id = null;
        this.salary = null;
        this.setFirstName(null);
        this.setLastName(null);
        this.country = null;
    }

    public Employee(Long id, String firstName, String lastName, Double salary) {
        this.id = id;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.salary = salary;
        this.country = null;
    }

    public Employee(Long id, String firstName, String lastName, String country, Double salary) {
        this.id = id;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.salary = salary;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, salary, country);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
