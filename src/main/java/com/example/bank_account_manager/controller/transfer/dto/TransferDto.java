package com.example.bank_account_manager.controller.transfer.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferDto {
    private Integer id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
}
