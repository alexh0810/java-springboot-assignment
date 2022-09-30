package com.example.springboot_assignment_free.model;

public class Pair {
    private String from_currency;
    private String to_currency;

    public String getFrom_currency() {
        return from_currency;
    }

    public void setFrom_currency(String from_currency) {
        this.from_currency = from_currency;
    }

    public String getTo_currency() {
        return to_currency;
    }

    public void setTo_currency(String to_currency) {
        this.to_currency = to_currency;
    }

    public Pair(String from_currency, String to_currency) {
        this.from_currency = from_currency;
        this.to_currency = to_currency;
    }
}
