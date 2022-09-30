package com.example.springboot_assignment_free.repository;

import com.example.springboot_assignment_free.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    // CUSTOM QUERY TO FIND EXCHANGE RATE BASED ON FROM, TO CURRENCY
    @Query("select rate from CurrencyRate rate where rate.from_currency = :from_currency and rate.to_currency = :to_currency")
    CurrencyRate findByFromToCurrency(@Param("from_currency") String from_currency,
                                      @Param("to_currency") String to_currency);

    // CUSTOM QUERY TO UPDATE EXCHANGE RATE
    @Modifying
    @Query("update CurrencyRate rate set rate.exchange_rate = :exchange_rate where rate.from_currency = :from_currency and rate.to_currency = :to_currency")
    void updateExchangeRate(@Param("exchange_rate") double exchange_rate,
                                    @Param("from_currency") String from_currency,
                                    @Param("to_currency") String to_currency);

    // CUSTOM QUERY TO INSERT EXCHANGE RATE
    @Modifying
    @Query(value = "insert into CurrencyRate (from_currency, to_currency, exchange_rate) VALUES (:from_currency, :to_currency, :exchange_rate)", nativeQuery = true)
    void insertExchangeRate(@Param("from_currency") String from_currency,
                                    @Param("to_currency") String to_currency,
                                    @Param("exchange_rate") double exchange_rate);
}

