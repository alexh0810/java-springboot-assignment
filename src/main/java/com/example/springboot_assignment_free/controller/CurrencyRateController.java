package com.example.springboot_assignment_free.controller;

import com.example.springboot_assignment_free.exceptions.CustomException;
import com.example.springboot_assignment_free.model.CurrencyRate;
import com.example.springboot_assignment_free.model.ExchangeRate;
import com.example.springboot_assignment_free.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.valueOf;

@RestController
public class CurrencyRateController {

    @Autowired
    private final CurrencyRateRepository currencyRepo;
    public CurrencyRateController(CurrencyRateRepository currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    @GetMapping("/api/v1/currencies-rate")
    @ResponseBody
    public ExchangeRate listRate(@RequestParam(name = "from_currency") String from_currency, @RequestParam(name = "to_currency") String to_currency, @RequestParam(name = "from_amount") double from_amount) {
        try {
            if (from_currency.isEmpty() || to_currency.isEmpty() || valueOf(from_amount).isEmpty()) {
                throw new CustomException("From, to currency and/or from amount must not be empty!");
            }
            CurrencyRate result = currencyRepo.findByFromToCurrency(from_currency.toUpperCase(), to_currency.toUpperCase());
            double exchange_amount = result.getExchange_rate() * from_amount;
            ExchangeRate exchange_result = new ExchangeRate(result.getFrom_currency(), result.getTo_currency(), exchange_amount);
            return exchange_result;
        } catch (RuntimeException e) {
            System.out.println("alex:" + e.getMessage());
            throw e;
        }
    }
    }


