package com.example.bank_account_manager.infrastructure.rest.exception;

import lombok.Getter;

@Getter
public class SameAccountException extends RuntimeException {
    private final String message;

    public SameAccountException(String message) {
        super(message);
        this.message = message;
    }
}
