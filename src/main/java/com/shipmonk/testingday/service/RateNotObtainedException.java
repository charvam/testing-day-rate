package com.shipmonk.testingday.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RateNotObtainedException extends ResponseStatusException {
    public RateNotObtainedException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
