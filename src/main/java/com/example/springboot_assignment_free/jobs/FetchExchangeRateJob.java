package com.example.springboot_assignment_free.jobs;

import com.example.springboot_assignment_free.model.CurrencyRate;
import com.example.springboot_assignment_free.model.ExchangeRateResponse;
import com.example.springboot_assignment_free.model.Pair;
import com.example.springboot_assignment_free.repository.CurrencyRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.*;

@Component
public class FetchExchangeRateJob {
    private static final Logger logger = LoggerFactory.getLogger(FetchExchangeRateJob.class);
    private final CurrencyRateRepository currencyRepo;

    public FetchExchangeRateJob(CurrencyRateRepository currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    @Scheduled(fixedRate = 3600000)
    public void scheduleHourlyFetch() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", "CKrFtBN8WQGwAyGSqf7SMa0CiL7ALwx5");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<Pair> currencies = new ArrayList<>();
        Pair usd_to_eur = new Pair("USD", "EUR");
        Pair eur_to_usd = new Pair("EUR", "USD");
        Pair usd_to_sek = new Pair("USD", "SEK");
        Pair sek_to_usd = new Pair("SEK", "USD");
        Pair eur_to_sek = new Pair("EUR", "SEK");
        Pair sek_to_eur = new Pair("SEK", "EUR");
        Collections.addAll(currencies, usd_to_eur, eur_to_usd, usd_to_sek, sek_to_usd, eur_to_sek, sek_to_eur);
        for (Pair currency : currencies) {
            String uri = "https://api.apilayer.com/exchangerates_data/convert?to="+currency.getTo_currency()+"&from="+currency.getFrom_currency()+"&amount=1";
            ExchangeRateResponse response = restTemplate.exchange(uri, HttpMethod.GET, entity, ExchangeRateResponse.class).getBody();
            CurrencyRate foundCurrencyPair = currencyRepo.findByFromToCurrency(currency.getFrom_currency(), currency.getTo_currency());
            if (foundCurrencyPair != null) {
                currencyRepo.updateExchangeRate(response.getResult(), currency.getFrom_currency(), currency.getTo_currency());
            } else {
                currencyRepo.insertExchangeRate(currency.getFrom_currency(), currency.getTo_currency(), response.getResult());
            }
        }
    }
}
