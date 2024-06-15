package com.subscription.model;
import java.time.LocalDateTime;
import java.util.List;

public class Subscriptions{

    public enum PeriodUnit{
        month, year
    }

    private int id;
    private Customers customer;
    private int billing_period;
    private PeriodUnit billing_period_unit;
    private int total_due;
    private LocalDateTime actived_at;
    private LocalDateTime current_term_start;
    private LocalDateTime current_term_end;
    private List<SubscriptionItems> subscriptionItems;

    public Subscriptions(){
        
    }
    public Subscriptions(int id, Customers customer, int billing_period, Subscriptions.PeriodUnit billing_period_unit,
            int total_due, LocalDateTime actived_at, LocalDateTime current_term_start, LocalDateTime current_term_end) {
        this.id = id;
        this.customer = customer;
        this.billing_period = billing_period;
        this.billing_period_unit = billing_period_unit;
        this.total_due = total_due;
        this.actived_at = actived_at;
        this.current_term_start = current_term_start;
        this.current_term_end = current_term_end;
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
    public void setCustomer(Customers customer) {
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
    public LocalDateTime getActived_at() {
        return actived_at;
    }
    public void setActived_at(LocalDateTime actived_at) {
        this.actived_at = actived_at;
    }
    public LocalDateTime getCurrent_term_start() {
        return current_term_start;
    }
    public void setCurrent_term_start(LocalDateTime current_term_start) {
        this.current_term_start = current_term_start;
    }
    public LocalDateTime getCurrent_term_end() {
        return current_term_end;
    }
    public void setCurrent_term_end(LocalDateTime current_term_end) {
        this.current_term_end = current_term_end;
    }
    public void setSubscriptionItems(List<SubscriptionItems> subscriptionItems) {
        this.subscriptionItems = subscriptionItems;
    }
}