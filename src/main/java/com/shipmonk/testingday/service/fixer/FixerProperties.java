package com.shipmonk.testingday.service.fixer;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "fixer")
@Getter
@Setter
public class FixerProperties {

    private String baseUrl;

    private String dateEndpoint;

    private String apiKey;

}
