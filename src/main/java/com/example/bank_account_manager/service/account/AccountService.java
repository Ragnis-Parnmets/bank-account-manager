package com.example.bank_account_manager.service.account;

import com.example.bank_account_manager.controller.account.dto.AccountCreateDto;
import com.example.bank_account_manager.controller.account.dto.AccountDto;
import com.example.bank_account_manager.controller.account.dto.AccountUpdateDto;
import com.example.bank_account_manager.infrastructure.rest.exception.AccountAlreadyExistsException;
import com.example.bank_account_manager.infrastructure.rest.exception.AccountNotFoundException;
import com.example.bank_account_manager.infrastructure.rest.exception.DataNotFoundException;
import com.example.bank_account_manager.persistence.account.*;
import com.example.bank_account_manager.persistence.accountholder.AccountHolder;
import com.example.bank_account_manager.persistence.accountholder.AccountHolderRepository;
import com.example.bank_account_manager.persistence.accounttype.AccountType;
import com.example.bank_account_manager.persistence.accounttype.AccountTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private static final String ACCOUNT_NOT_FOUND = "Account with id %d not found";

    private final AccountRepository accountRepository;
    private final AccountHolderRepository accountHolderRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountMapper accountMapper;

    @Transactional(readOnly = true)
    public List<AccountDto> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AccountDto findById(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND.formatted(id)));
        return accountMapper.toDto(account);
    }

    public AccountDto create(AccountCreateDto dto) {
        String accountNumber = dto.getAccountNumber();
        if (accountRepository.existsByAccountNumber(accountNumber)) {
            throw new AccountAlreadyExistsException("Account already exists. Account number must be unique.");
        }

        Account account = accountMapper.toEntity(dto);
        account.setCreatedAt(Instant.now());
        account.setAccountHolder(resolveAccountHolder(dto.getAccountHolderId()));
        account.setAccountType(resolveAccountType(dto.getAccountTypeId()));
        Account saved = accountRepository.save(account);
        return accountMapper.toDto(saved);
    }

    public AccountDto update(Integer id, AccountUpdateDto dto) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND.formatted(id)));

        // If an account number is provided and changed, ensure uniqueness against other accounts
        if (dto.getAccountNumber() != null) {
            String newNumber = dto.getAccountNumber();
            if (!newNumber.equals(existing.getAccountNumber())
                    && accountRepository.existsByAccountNumberAndIdNot(newNumber, id)) {
                throw new AccountAlreadyExistsException("Account already exists. Account number must be unique.");
            }
        }

        accountMapper.updateEntity(dto, existing);
        existing.setAccountHolder(resolveAccountHolder(dto.getAccountHolderId()));
        existing.setAccountType(resolveAccountType(dto.getAccountTypeId()));
        Account saved = accountRepository.save(existing);
        return accountMapper.toDto(saved);
    }

    public void delete(Integer id) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND.formatted(id)));
        accountRepository.delete(existing);
    }

    private AccountHolder resolveAccountHolder(Integer id) {
        return accountHolderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Account holder with id %d not found".formatted(id)));
    }

    private AccountType resolveAccountType(Integer id) {
        return accountTypeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Account type with id %d not found".formatted(id)));
    }
}
