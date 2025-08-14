package com.example.bank_account_manager.persistence.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNT_HOLDER")
public class AccountHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;

    @Size(max = 100)
    @NotNull
    @Column(name = "LAST_NAME", nullable = false, length = 100)
    private String lastName;

    @NotNull
    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

}