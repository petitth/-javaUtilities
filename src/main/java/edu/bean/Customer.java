package edu.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Annotations are used to map a json file to this bean :
 * <ul>
 *     <li>JsonSetter provides the field name in the json file</li>
 *     <li>JsonIgnoreProperties is used to ignore json fields that are not declared here</li>
 * </ul>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    private String companyName;
    private String address;
    private String phone;
    private String postalCode;
    private String country;
    private String customerId;
    private String city;
    private String fax;
    private String contactName;
    private String contactTitle;

    public Customer() {
        super();
    }

    public String getCompanyName() {
        return companyName;
    }

    @JsonSetter("CompanyName")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    @JsonSetter("Address")
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    @JsonSetter("Phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @JsonSetter("PostalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    @JsonSetter("Country")
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCustomerId() {
        return customerId;
    }

    @JsonSetter("CustomerID")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCity() {
        return city;
    }

    @JsonSetter("City")
    public void setCity(String city) {
        this.city = city;
    }

    public String getFax() {
        return fax;
    }

    @JsonSetter("Fax")
    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContactName() {
        return contactName;
    }

    @JsonSetter("ContactName")
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    @JsonSetter("ContactTitle")
    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

}