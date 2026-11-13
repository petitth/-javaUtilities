package edu.bean;

public class Address {

    private String streetName;
    private String streetNo;
    private String postCode;
    private String city;
    private String country;

    public Address() {
        this.streetName = null;
        this.streetNo = null;
        this.postCode = null;
        this.city = null;
        this.country = null;
    }

    public Address(String streetNo, String streetName, String postCode, String city, String country) {
        this.streetNo = streetNo;
        this.streetName = streetName;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return streetNo + " " + streetName + " - " + postCode + " " + city + " (" + country + ")";
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }
}