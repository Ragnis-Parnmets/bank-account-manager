package com.example.bank_account_manager.infrastructure.rest.exception;

import lombok.Getter;

@Getter
public class AccountTypeNotFoundException extends RuntimeException {
    private final String message;

    public AccountTypeNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
