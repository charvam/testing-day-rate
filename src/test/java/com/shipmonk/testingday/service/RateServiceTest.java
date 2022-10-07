package com.shipmonk.testingday.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shipmonk.testingday.model.dto.RatesDto;

@ExtendWith(MockitoExtension.class)
class RateServiceTest {

    private static final String BASE_CURRENCY = "USD";

    private static final LocalDate DATE = LocalDate.EPOCH;

    private RateService service;

    @Mock
    private RateProvider rateProvider;

    @Mock
    private RateStorage rateStorage;

    @Test
    void getRates_noProviderFound() {
        service = new RateService(List.of(), rateStorage);

        assertThrows(RateNotObtainedException.class, () -> service.getRates(BASE_CURRENCY, DATE));
    }

    @Test
    void getRates_noRateFound() {
        service = new RateService(List.of(rateProvider), rateStorage);
        when(rateProvider.getRates(BASE_CURRENCY, DATE)).thenThrow(RateProviderException.class);

        assertThrows(RateNotObtainedException.class, () -> service.getRates(BASE_CURRENCY, DATE));

        verifyNoInteractions(rateStorage);
    }


    @Test
    void getRates_found() {
        RatesDto ratesDto = RatesDto.builder().build();
        service = new RateService(List.of(rateProvider), rateStorage);
        when(rateProvider.getRates(BASE_CURRENCY, DATE)).thenReturn(ratesDto);
        doNothing().when(rateStorage).insert(BASE_CURRENCY, DATE, ratesDto);

        final RatesDto resultRatesDto = service.getRates(BASE_CURRENCY, DATE);

        assertThat(resultRatesDto).isEqualTo(ratesDto);
        verify(rateStorage).insert(BASE_CURRENCY, DATE, ratesDto);
    }

}
