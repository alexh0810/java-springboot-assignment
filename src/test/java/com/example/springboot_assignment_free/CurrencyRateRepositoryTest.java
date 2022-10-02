package com.example.springboot_assignment_free;

import com.example.springboot_assignment_free.model.CurrencyRate;
import com.example.springboot_assignment_free.repository.CurrencyRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(properties = "app.exchange_rate.api_key=abc")
class CurrencyRateRepositoryTest {

    @Autowired
    CurrencyRateRepository currencyRepo;

    @Test
    void contextLoads() {
    }
    @Test
    public void testGetAllCurrencies () {
        List<CurrencyRate> currencyRateList = currencyRepo.findAll();
        assertThat(currencyRateList).size().isGreaterThan(0);
    }
    @Test
    public void testFindByFromTo () {
        CurrencyRate currencyRate = currencyRepo.findByFromToCurrency("USD", "EUR");
        assertThat(currencyRate).isNotNull();
    }

}
