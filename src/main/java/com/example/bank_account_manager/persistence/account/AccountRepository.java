package com.example.bank_account_manager.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByAccountNumber(String accountNumber);
    boolean existsByAccountNumberAndIdNot(String accountNumber, Integer id);

    List<Account> findAllByStatus(String status);
    Optional<Account> findByIdAndStatus(Integer id, String status);
}
