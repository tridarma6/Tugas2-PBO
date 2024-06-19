package com.subscription.model;
public class ShippingAddress {
    private int id;
    private Customers customer;
    private String title;
    private String line1;
    private String line2;
    private String city;
    private String province;
    private String postcode;

    public ShippingAddress() {

    }
    
    public ShippingAddress(int id, Customers customer, String title, String line1, String line2, String city, String province, String postcode) {
        this.id = id;
        this.customer = customer;
        this.title = title;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.province = province;
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = Customers.valueOf(customer);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCustomer(Customers customer2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCustomer'");
    }
}