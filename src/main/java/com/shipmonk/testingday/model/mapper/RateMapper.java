package com.shipmonk.testingday.model.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.shipmonk.testingday.model.domain.Rate;
import com.shipmonk.testingday.model.dto.RatesDto;
import com.shipmonk.testingday.model.domain.Base;

@Component
public class RateMapper {

    public Base toBase(RatesDto ratesDto) {
        final Base base = new Base();

        base.setDate(ratesDto.getDate());

        base.setCurrency(ratesDto.getBaseCurrency());

        final List<Rate> rates = new ArrayList<>();

        ratesDto.getRates()
            .forEach((currency, rate) -> rates.add(new Rate(currency, rate, base)));

        base.setRates(rates);

        return base;
    }

    public RatesDto toRatesDto(Base base) {
        final Map<String, BigDecimal> rates = new HashMap<>();

        base.getRates()
            .forEach(rate -> rates.put(rate.getCurrency(), rate.getRate()));

        return RatesDto.builder()
            .baseCurrency(base.getCurrency())
            .date(base.getDate())
            .rates(rates)
            .build();

    }

}
