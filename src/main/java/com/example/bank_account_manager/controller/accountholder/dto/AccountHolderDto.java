package com.example.bank_account_manager.controller.accountholder.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountHolderDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
}
