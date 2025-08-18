package com.example.bank_account_manager.controller.accountholder.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountHolderUpdateDto {
    @Size(max = 100)
    @NotNull
    private String firstName;

    @Size(max = 100)
    @NotNull
    private String lastName;
}
