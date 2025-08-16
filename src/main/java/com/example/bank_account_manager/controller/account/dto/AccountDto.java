package com.example.bank_account_manager.controller.account.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class AccountDto {
    private Integer id;
    private String accountNumber;
    private Integer accountHolderId;
    private String accountHolder;
    private Integer accountTypeId;
    private String accountType;
    private String balance;
    private Instant createdAt;
}
