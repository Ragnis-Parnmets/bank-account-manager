package com.example.bank_account_manager.persistence.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNT_TYPE")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "TYPE_NAME", nullable = false, length = 100)
    private String typeName;

    @Size(max = 255)
    @NotNull
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

}