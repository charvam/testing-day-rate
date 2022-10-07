package com.shipmonk.testingday.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RatesDto {

    private LocalDate date;

    private String baseCurrency;

    private Map<String, BigDecimal> rates;
}
