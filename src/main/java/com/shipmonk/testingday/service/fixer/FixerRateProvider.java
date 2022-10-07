package com.shipmonk.testingday.service.fixer;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.shipmonk.testingday.model.dto.RatesDto;
import com.shipmonk.testingday.service.RateProvider;
import com.shipmonk.testingday.service.RateProviderException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FixerRateProvider implements RateProvider {

    private final FixerProperties fixerProviderProperties;

    private final RestTemplate restTemplate;

    public FixerRateProvider(FixerProperties fixerProviderProperties, RestTemplate restTemplate) {
        this.fixerProviderProperties = fixerProviderProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public RatesDto getRates(String base, LocalDate date) {

        final ResponseEntity<FixerApiResponse> response;

        try {
            final String dateRateUrl = fixerProviderProperties.getBaseUrl() + fixerProviderProperties.getDateEndpoint();
            response = restTemplate.getForEntity(dateRateUrl, FixerApiResponse.class, date, base);
        } catch (RestClientException e) {
            log.error("Error occurred while rate obtaining.", e);
            throw new RateProviderException("No rate obtained, message:" + e.getMessage());
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            final FixerApiResponse responseBody = response.getBody();
            if (responseBody.isSuccess()) {
                return responseBody.ratesDto();
            } else {
                //TODO extends with Error
                throw new RateProviderException("Bad request, error code: ");
            }
        } else {
            throw new RateProviderException("Server error, status code: " + response.getStatusCode().value());
        }
    }

}
