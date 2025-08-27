package com.example.bank_account_manager.service.transfer;

import com.example.bank_account_manager.controller.transfer.dto.TransferCreateDto;
import com.example.bank_account_manager.controller.transfer.dto.TransferDto;
import com.example.bank_account_manager.infrastructure.rest.exception.AccountNotFoundException;
import com.example.bank_account_manager.infrastructure.rest.exception.DataNotFoundException;
import com.example.bank_account_manager.infrastructure.rest.exception.InsufficientFundsException;
import com.example.bank_account_manager.infrastructure.rest.exception.PositiveAmountException;
import com.example.bank_account_manager.infrastructure.rest.exception.SameAccountException;
import com.example.bank_account_manager.persistence.account.Account;
import com.example.bank_account_manager.persistence.account.AccountRepository;
import com.example.bank_account_manager.persistence.transfer.Transfer;
import com.example.bank_account_manager.persistence.transfer.TransferMapper;
import com.example.bank_account_manager.persistence.transfer.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransferService {

    private static final String ACTIVE = "ACTIVE";

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransferMapper transferMapper;

    @Transactional(readOnly = true)
    public List<TransferDto> findAll() {
        return transferRepository.findAll()
                .stream()
                .map(transferMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TransferDto findById(Integer id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Transfer with id %d not found".formatted(id)));
        return transferMapper.toDto(transfer);
    }

    public TransferDto create(TransferCreateDto dto) {
        validateTransfer(dto);

        Account from = findActiveAccount(dto.getFromAccount(), true);
        Account to = findActiveAccount(dto.getToAccount(), false);

        // Ensure sufficient funds
        if (from.getBalance().compareTo(dto.getAmount()) < 0) {
            log.warn("Transfer failed: insufficient funds in the source account (id={})", from.getId());
            throw new InsufficientFundsException("Insufficient funds in the source account");
        }

        // Update balances
        BigDecimal amount = dto.getAmount();
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);

        // Save transfer record
        Transfer transfer = transferMapper.toEntity(dto);
        transfer.setCreatedAt(Instant.now());
        Transfer saved = transferRepository.save(transfer);
        log.info("Successful transfer created (id={})", saved.getId());
        return transferMapper.toDto(saved);
    }

    private Account findActiveAccount(String number, boolean source) {
        return accountRepository.findByAccountNumberAndStatus(number, ACTIVE)
                .orElseThrow(() -> new AccountNotFoundException(
                        (source ? "From account with number %s not found" : "To account with number %s not found").formatted(number)
                ));
    }

    private void validateTransfer(TransferCreateDto dto) {
        if (dto.getAmount() == null || dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PositiveAmountException("Transfer amount must be positive");
        }
        if (dto.getFromAccount() != null && dto.getFromAccount().equals(dto.getToAccount())) {
            throw new SameAccountException("Transfer between the same account is not allowed");
        }
    }
}
