package com.example.bank_account_manager.controller.accounttype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountTypeUpdateDto {
    @Size(max = 100)
    @NotBlank
    private String typeName;

    @Size(max = 255)
    @NotBlank
    private String description;
}
