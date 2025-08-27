package com.example.bank_account_manager.controller.transfer.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferCreateDto {

    private static final String ACCOUNT_NUMBER_PATTERN = "Account number must be exactly 20 characters without spaces (2 letters + 18 numbers)";

    @Pattern(regexp = "[A-Z]{2}\\d{18}", message = ACCOUNT_NUMBER_PATTERN)
    private String fromAccount;

    @Pattern(regexp = "[A-Z]{2}\\d{18}", message = ACCOUNT_NUMBER_PATTERN)
    private String toAccount;

    @Positive
    @NotNull
    private BigDecimal amount;

    @Size(max = 255)
    @NotBlank
    private String description;
}
