package com.example.bank_account_manager.infrastructure.rest.exception;

import lombok.Getter;

@Getter
public class AccountHolderNotFoundException extends RuntimeException {
    private final String message;

    public AccountHolderNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
