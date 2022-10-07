package com.shipmonk.testingday.service.fixer;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.shipmonk.testingday.model.dto.RatesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FixerApiResponse {
    private String base;

    private LocalDate date;

    private Map<String, BigDecimal> rates;

    private boolean success;

    RatesDto ratesDto() {
        return RatesDto.builder()
            .date(date)
            .baseCurrency(base)
            .rates(rates)
            .build();
    }
}
