package com.shipmonk.testingday.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import com.shipmonk.testingday.model.dto.RatesDto;
import com.shipmonk.testingday.service.RateService;

@RestController
@RequestMapping(
    path = "/api/v1/rates"
)
public class RatesController
{
    private static final String BASE_CURRENCY = "USD";

    private final RateService rateService;

    public RatesController(RateService rateService) {
        this.rateService = rateService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{day}")
    public RatesDto getRates(@PathVariable("day") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return rateService.getRates(BASE_CURRENCY, date);
    }
}
