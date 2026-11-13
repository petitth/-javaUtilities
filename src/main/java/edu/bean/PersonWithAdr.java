package edu.bean;

import java.util.List;
import java.util.Objects;

public class PersonWithAdr extends Person {

    private List<Address> addressList;

    public PersonWithAdr(String firstName, String lastName) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        this.addressList = null;
    }

    public PersonWithAdr(String firstName, String lastName, List<Address> addressList) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        this.addressList = addressList;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), addressList);
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

}