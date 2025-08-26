package com.example.bank_account_manager.controller.account.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreateDto {
    @Size(max = 20)
    @NotBlank
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
