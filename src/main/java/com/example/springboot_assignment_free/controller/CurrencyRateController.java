package com.example.springboot_assignment_free.controller;

import com.example.springboot_assignment_free.model.CurrencyRate;
import com.example.springboot_assignment_free.model.ExchangeRate;
import com.example.springboot_assignment_free.repository.CurrencyRateRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CurrencyRateController {

    private final CurrencyRateRepository currencyRepo;

    public CurrencyRateController(CurrencyRateRepository currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    @GetMapping("/api/v1/currencies-rate")
    @ResponseBody
    public ExchangeRate listRate(@RequestParam(name = "from_currency") String from_currency, @RequestParam(name = "to_currency") String to_currency, @RequestParam(name = "from_amount") double from_amount) {
        CurrencyRate result = currencyRepo.findByFromToCurrency(from_currency, to_currency);
        double exchange_amount = result.getExchange_rate() * from_amount;
        ExchangeRate exchange_result = new ExchangeRate();
        exchange_result.from_currency = result.getFrom_currency();
        exchange_result.to_currency = result.getTo_currency();
        exchange_result.exchange_amount = exchange_amount;
        return exchange_result;
    }
}
