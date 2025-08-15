package com.example.bank_account_manager.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
}
