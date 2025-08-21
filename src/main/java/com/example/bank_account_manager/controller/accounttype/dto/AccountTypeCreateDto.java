package com.example.bank_account_manager.controller.accounttype.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountTypeCreateDto {
    @Size(max = 100)
    @NotNull
    private String typeName;

    @Size(max = 255)
    @NotNull
    private String description;
}
