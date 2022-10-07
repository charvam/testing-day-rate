package com.shipmonk.testingday.service;

import java.time.LocalDate;

import com.shipmonk.testingday.model.dto.RatesDto;

public interface RateProvider {
     RatesDto getRates(String baseCurrency, LocalDate date);
}
