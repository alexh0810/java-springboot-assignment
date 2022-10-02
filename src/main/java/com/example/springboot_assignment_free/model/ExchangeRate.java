package com.example.springboot_assignment_free.model;


public class ExchangeRate {
    public String from_currency;
   public String to_currency;
   public double exchange_amount;

    public ExchangeRate(String from_currency, String to_currency, double exchange_amount) {
        this.from_currency = from_currency;
        this.to_currency = to_currency;
        this.exchange_amount = exchange_amount;
    }
}
