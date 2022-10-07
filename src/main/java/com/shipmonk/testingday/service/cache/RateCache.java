package com.shipmonk.testingday.service.cache;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.shipmonk.testingday.model.domain.Base;
import com.shipmonk.testingday.model.dto.RatesDto;
import com.shipmonk.testingday.model.mapper.RateMapper;
import com.shipmonk.testingday.repository.BaseRepository;
import com.shipmonk.testingday.service.RateProvider;
import com.shipmonk.testingday.service.RateProviderException;
import com.shipmonk.testingday.service.RateStorage;

@Component
public class RateCache implements RateStorage, RateProvider {

    private final BaseRepository rateRepository;

    private final RateMapper rateMapper;

    public RateCache(BaseRepository rateRepository, RateMapper rateMapper) {
        this.rateRepository = rateRepository;
        this.rateMapper = rateMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public RatesDto getRates(String baseCurrency, LocalDate date) {
        return rateRepository.findBaseByDateAndCurrency(date, baseCurrency)
            .map(rateMapper::toRatesDto)
            .orElseThrow(() ->  new RateProviderException(String.format("No rate found for baseCurrency %s and date %s.", baseCurrency, date)));
    }

    @Override
    public void insert(String baseCurrency, LocalDate date, RatesDto ratesDto) {
        final Optional<Base> base = rateRepository.findBaseByDateAndCurrency(date, baseCurrency);

        if (base.isEmpty()) {
            final Base baseNew = rateMapper.toBase(ratesDto);
            rateRepository.save(baseNew);
        }

    }
}
