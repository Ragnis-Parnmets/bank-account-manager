package com.example.bank_account_manager.controller.accountholder.dto;

import lombok.Data;


@Data
public class AccountHolderDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String createdAt;
}
