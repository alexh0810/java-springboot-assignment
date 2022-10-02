package com.example.springboot_assignment_free.controllers;

import com.example.springboot_assignment_free.controller.CurrencyRateController;
import com.example.springboot_assignment_free.model.CurrencyRate;
import com.example.springboot_assignment_free.repository.CurrencyRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyRateControllerTest {
    private MockMvc mockMvc;
    @Mock
    CurrencyRateRepository currencyRepo;

    @InjectMocks
    private CurrencyRateController currencyRateController;
    CurrencyRate USD_TO_EUR = new CurrencyRate(1L, "USD", "EUR", 1.00);
    CurrencyRate EUR_TO_USD = new CurrencyRate(2L, "EUR", "USD", 0.93);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(currencyRateController).build();
    }

    @Test
    public void testGetExchangeRate() throws Exception {
        List<CurrencyRate> rates = new ArrayList<>(Arrays.asList(USD_TO_EUR, EUR_TO_USD));
        Mockito.when(currencyRepo.findByFromToCurrency("USD", "EUR")).thenReturn(rates.get(0));

        CurrencyRate result = currencyRepo.findByFromToCurrency("USD", "EUR");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("from_currency", "USD");
        params.add("to_currency", "EUR");
        params.add("from_amount", "1.00");
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/v1/currencies-rate")
                .params(params)
                .contentType(MediaType.APPLICATION_JSON);
      mockMvc.perform(mockRequest)
              .andExpect(status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$.exchange_amount").value("1.0"));
    }
}