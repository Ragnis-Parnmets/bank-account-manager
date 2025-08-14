package com.example.bank_account_manager.persistence.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "ACCOUNT_NUMBER", nullable = false, length = 20)
    private String accountNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ACCOUNT_HOLDER_ID", nullable = false)
    private AccountHolder accountHolder;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ACCOUNT_TYPE_ID", nullable = false)
    private AccountType accountType;

    @NotNull
    @Column(name = "BALANCE", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @NotNull
    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

}