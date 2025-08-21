package com.example.bank_account_manager.persistence.accounttype;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
    List<AccountType> findAllByStatus(String status);

    Optional<AccountType> findByIdAndStatus(Integer id, String status);
}
