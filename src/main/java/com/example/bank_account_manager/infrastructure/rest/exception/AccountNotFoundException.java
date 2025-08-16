package com.example.bank_account_manager.infrastructure.rest.exception;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException {
    private final String message;

    public AccountNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
