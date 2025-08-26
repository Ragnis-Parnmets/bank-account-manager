package com.example.bank_account_manager.controller.accountholder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountHolderCreateDto {
    @Size(max = 100)
    @NotBlank
    private String firstName;

    @Size(max = 100)
    @NotBlank
    private String lastName;
}
