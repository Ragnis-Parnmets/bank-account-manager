package com.example.bank_account_manager.persistence.accountholder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
}
