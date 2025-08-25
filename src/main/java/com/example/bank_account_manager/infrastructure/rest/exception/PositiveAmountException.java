package com.example.bank_account_manager.infrastructure.rest.exception;

import lombok.Getter;

@Getter
public class PositiveAmountException extends RuntimeException {
    private final String message;

    public PositiveAmountException(String message) {
        super(message);
        this.message = message;
    }
}
