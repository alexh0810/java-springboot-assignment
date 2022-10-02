package com.example.springboot_assignment_free.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRate {

    @JsonProperty("from")
    public String from;
    @JsonProperty("to")
   public String to;
   @JsonProperty("to_amount")
    public double toAmount;


    public ExchangeRate(String from, String to, double toAmount) {
        this.from = from;
        this.to = to;
        this.toAmount = toAmount;
    }
}
