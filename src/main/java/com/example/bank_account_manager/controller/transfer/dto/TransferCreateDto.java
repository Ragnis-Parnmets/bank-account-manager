package com.example.bank_account_manager.controller.transfer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferCreateDto {
    @Size(max = 20)
    @NotNull
    private String fromAccount;

    @Size(max = 20)
    @NotNull
    private String toAccount;

    @Positive
    @NotNull
    private BigDecimal amount;

    @Size(max = 255)
    @NotNull
    private String description;
}
