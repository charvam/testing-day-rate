package com.shipmonk.testingday.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.shipmonk.testingday.model.dto.RatesDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RateService {

    private final List<RateProvider> rateProviders;

    private final RateStorage rateStorage;

    public RateService(List<RateProvider> rateProviders, RateStorage rateStorage) {
        this.rateProviders = rateProviders;
        this.rateStorage = rateStorage;
    }

    public RatesDto getRates(String baseCurrency, LocalDate date) {

        if (rateProviders.isEmpty()) {
            throw new RateNotObtainedException(HttpStatus.INTERNAL_SERVER_ERROR, "No rate provider found.");
        }

        for (final RateProvider rateProvider : rateProviders) {
            final Optional<RatesDto> ratesDtoResult = obtainRates(rateProvider, baseCurrency, date);
            if (ratesDtoResult.isPresent()) {
                final RatesDto ratesDto = ratesDtoResult.get();
                //TODO Insert only if provider is not storage
                rateStorage.insert(baseCurrency, date, ratesDto);
                return ratesDto;
            }
        }

        throw new RateNotObtainedException(HttpStatus.NOT_FOUND, String.format("No rate provider contents baseCurrency for base %s and date %s.", baseCurrency, date));
    }

    private Optional<RatesDto> obtainRates(RateProvider rateProvider, String baseCurrency, LocalDate date) {
        try {
            final RatesDto rates = rateProvider.getRates(baseCurrency, date);
            return Optional.of(rates);
        } catch (Exception e) {
            log.error("No rate found for baseCurrency {} and date {} in provider {}. ", baseCurrency, date, rateProvider.getClass().getName(), e);
            return Optional.empty();
        }
    }
}
