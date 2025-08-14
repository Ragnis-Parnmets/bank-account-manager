package com.example.bank_account_manager.infrastructure.rest.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {
    private final String message;

    public DataNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
