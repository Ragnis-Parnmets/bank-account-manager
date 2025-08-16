package com.example.bank_account_manager.controller.account.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreateDto {
    @Size(max = 20)
    @NotNull
    private String accountNumber;

    @NotNull
    private Integer accountHolderId;

    @NotNull
    private Integer accountTypeId;

    @NotNull
    private BigDecimal balance;
}
