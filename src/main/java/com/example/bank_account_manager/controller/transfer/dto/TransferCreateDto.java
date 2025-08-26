package com.example.bank_account_manager.controller.transfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferCreateDto {
    @Size(max = 20)
    @NotBlank
    private String fromAccount;

    @Size(max = 20)
    @NotBlank
    private String toAccount;

    @Positive
    @NotNull
    private BigDecimal amount;

    @Size(max = 255)
    @NotBlank
    private String description;
}
