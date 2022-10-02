package com.example.springboot_assignment_free.model;


import javax.persistence.*;

@Entity
@Table(name = "currency_rate")
public class CurrencyRate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column private String from_currency;
    @Column private String to_currency;
    @Column private double exchange_rate;

    public CurrencyRate() {
    }

    public CurrencyRate(Long id, String from_currency, String to_currency, double exchange_rate) {
        this.id = id;
        this.from_currency = from_currency;
        this.to_currency = to_currency;
        this.exchange_rate = exchange_rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public double getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(double exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

}
