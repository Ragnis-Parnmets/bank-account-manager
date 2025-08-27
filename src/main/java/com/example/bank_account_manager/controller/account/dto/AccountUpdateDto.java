package com.example.bank_account_manager.controller.account.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountUpdateDto {
    @Pattern(regexp = "[A-Z]{2}\\d{18}", message = "Account number must be exactly 20 characters without spaces (2 letters + 18 numbers)")
    private String accountNumber;

    @NotNull
    @Positive
    private Integer accountHolderId;

    @NotNull
    @Positive
    private Integer accountTypeId;

    @NotNull
    @PositiveOrZero
    private BigDecimal balance;
}
