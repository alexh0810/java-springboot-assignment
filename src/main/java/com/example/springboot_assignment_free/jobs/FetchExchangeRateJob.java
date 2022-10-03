package com.example.springboot_assignment_free.jobs;

import com.example.springboot_assignment_free.model.CurrencyRate;
import com.example.springboot_assignment_free.model.ExchangeRateResponse;
import com.example.springboot_assignment_free.model.Pair;
import com.example.springboot_assignment_free.repository.CurrencyRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class FetchExchangeRateJob {
    private static final Logger logger = LoggerFactory.getLogger(FetchExchangeRateJob.class);
    private final CurrencyRateRepository currencyRepo;

    @Value("${app.exchange_rate.api_key}")
    private String API_KEY;

    public FetchExchangeRateJob(CurrencyRateRepository currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    @Scheduled(fixedRate = 3600000)
    public void scheduleHourlyFetch() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_KEY);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        RestTemplate restTemplate = new RestTemplate();
        List<Pair> currencies = List.of(
                new Pair("USD", "EUR"),
                new Pair("EUR", "USD"),
                new Pair("USD", "SEK"),
                new Pair("EUR", "SEK"),
                new Pair("SEK", "EUR")
        );
        for (Pair currency : currencies) {
            String uri = "https://api.apilayer.com/exchangerates_data/convert?to="+currency.getTo()+"&from="+currency.getFrom()+"&amount=1";
            ExchangeRateResponse response = restTemplate.exchange(uri, HttpMethod.GET, entity, ExchangeRateResponse.class).getBody();
            CurrencyRate foundCurrencyPair = currencyRepo.findByFromToCurrency(currency.getFrom(), currency.getTo());
            if (foundCurrencyPair != null) {
                currencyRepo.updateExchangeRate(response.getResult(), currency.getFrom(), currency.getTo());
            } else {
                currencyRepo.insertExchangeRate(currency.getFrom(), currency.getTo(), response.getResult());
            }
        }
    }
}
