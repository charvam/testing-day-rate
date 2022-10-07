package com.shipmonk.testingday.service;

import java.time.LocalDate;

import com.shipmonk.testingday.model.dto.RatesDto;

public interface RateStorage {

    void insert(String baseCurrency, LocalDate date, RatesDto ratesDto);

}
