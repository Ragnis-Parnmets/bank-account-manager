package com.example.bank_account_manager.persistence.accountholder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
    List<AccountHolder> findAllByStatus(String status);

    Optional<AccountHolder> findByIdAndStatus(Integer id, String status);
}
