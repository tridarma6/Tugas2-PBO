package com.subscription.model;

import java.util.List;

public class Subscriptions {

    public enum PeriodUnit {
        month, year
    }

    public enum Status {
        active, cancelled, nonrenewing
    }

    private int id;
    private int customer;
    private int billing_period;
    private PeriodUnit billing_period_unit;
    private int total_due;
    private int actived_at;
    private String current_term_start;
    private String current_term_end;
    private Status status;

    private List<SubscriptionItems> subscriptionItems;
    private Customers customerDetails;  // Mengubah menjadi single Customers object

    public Subscriptions() {
    }

    public Subscriptions(int id, int customer, int billing_period, PeriodUnit billing_period_unit,
                         int total_due, int actived_at, String current_term_start, String current_term_end) {
        this.id = id;
        this.customer = customer;
        this.billing_period = billing_period;
        this.billing_period_unit = billing_period_unit;
        this.total_due = total_due;
        this.actived_at = actived_at;
        this.current_term_start = current_term_start;
        this.current_term_end = current_term_end;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
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

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getBilling_period() {
        return billing_period;
    }

    public void setBilling_period(int billing_period) {
        this.billing_period = billing_period;
    }

    public PeriodUnit getBilling_period_unit() {
        return billing_period_unit;
    }

    public void setBilling_period_unit(String billing_period_unit) {
        this.billing_period_unit = PeriodUnit.valueOf(billing_period_unit);
    }

    public int getTotal_due() {
        return total_due;
    }

    public void setTotal_due(int total_due) {
        this.total_due = total_due;
    }

    public int getActived_at() {
        return actived_at;
    }

    public void setActived_at(int actived_at) {
        this.actived_at = actived_at;
    }

    public String getCurrent_term_start() {
        return current_term_start;
    }

    public void setCurrent_term_start(String current_term_start) {
        this.current_term_start = current_term_start;
    }

    public String getCurrent_term_end() {
        return current_term_end;
    }

    public void setCurrent_term_end(String current_term_end) {
        this.current_term_end = current_term_end;
    }

    public void setSubscriptionItems(List<SubscriptionItems> subscriptionItems) {
        this.subscriptionItems = subscriptionItems;
    }

    public List<SubscriptionItems> getSubscriptionItems() {
        return subscriptionItems;
    }

    public Customers getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(Customers customerDetails) {
        this.customerDetails = customerDetails;
    }
}
