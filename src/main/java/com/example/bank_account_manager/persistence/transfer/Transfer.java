package com.example.bank_account_manager.persistence.transfer;

import com.example.bank_account_manager.persistence.account.Account;
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
@Table(name = "TRANSFER")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @Size(max = 100)
    @NotNull
    @Column(name = "TRANSFER_TYPE", nullable = false, length = 100)
    private String transferType;

    @NotNull
    @Column(name = "AMOUNT", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(name = "TRANSFER_DATE", nullable = false)
    private Instant transferDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

}