package com.shipmonk.testingday.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RatesRestTemplateConfig {

    private static final String HEADER_APIKEY = "apikey";

    @Bean
    public RestTemplate ratesRestTemplate(Interceptor interceptor) {
        //TODO set timeouts....from properties
        return new RestTemplateBuilder()
            .interceptors(interceptor)
            .build();
    }

    @Component
    public static class Interceptor implements ClientHttpRequestInterceptor {

        private final String apiKey;

        public Interceptor(@Value("${fixer.apikey}") String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().add(HEADER_APIKEY, apiKey);
            return execution.execute(request, body);
        }
    }
}
