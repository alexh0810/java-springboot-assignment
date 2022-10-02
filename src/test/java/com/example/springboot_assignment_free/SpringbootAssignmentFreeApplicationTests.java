package com.example.springboot_assignment_free;

import com.example.springboot_assignment_free.controller.CurrencyRateController;
import com.example.springboot_assignment_free.model.CurrencyRate;
import com.example.springboot_assignment_free.model.ExchangeRate;
import com.example.springboot_assignment_free.model.SSN;
import com.example.springboot_assignment_free.repository.CurrencyRateRepository;
import com.example.springboot_assignment_free.services.ValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
class SpringbootAssignmentFreeApplicationTests {

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
