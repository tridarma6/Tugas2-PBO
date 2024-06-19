package com.subscription.model;
public class Cards{
    public enum Type{
        visa, mastercard;
    }
    public enum Status{
        valid, expiring, expired
    }
    private int id;
    // private Customers customer;
    private int customer;
    private Type card_type;
    private String masked_number;
    private int expiry_month;
    private int expiry_year;
    private Status status;
    private int is_primary;

    public Cards(){
        
    }
    public Cards(int id, int customer, Cards.Type card_type, String masked_number, int expiry_month, int expiry_year,
            Cards.Status status, int is_primary) {
        this.id = id;
        this.customer = customer;
        this.card_type = card_type;
        this.masked_number = masked_number;
        this.expiry_month = expiry_month;
        this.expiry_year = expiry_year;
        this.status = status;
        this.is_primary = is_primary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer;
    }

    public int getCustomerId(){
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public Type getCard_type() {
        return card_type;
    }

    public void setCard_type(Type card_type) {
        this.card_type = card_type;
    }

    public void setCard_type(String card_type){
        this.card_type = Type.valueOf(card_type);
    }

    public String getMasked_number() {
        return masked_number;
    }

    public void setMasked_number(String masked_number) {
        this.masked_number = masked_number;
    }

    public int getExpiry_month() {
        return expiry_month;
    }

    public void setExpiry_month(int expiry_month) {
        this.expiry_month = expiry_month;
    }

    public int getExpiry_year() {
        return expiry_year;
    }

    public void setExpiry_year(int expiry_year) {
        this.expiry_year = expiry_year;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatus(String status){
        this.status = Status.valueOf(status);
    }

    public int getIs_primary() {
        return is_primary;
    }

    public void setIs_primary(int is_primary) {
        this.is_primary = is_primary;
    }

}
