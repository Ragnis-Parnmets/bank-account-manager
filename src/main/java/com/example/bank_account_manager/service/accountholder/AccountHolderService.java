package com.example.bank_account_manager.service.accountholder;

import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderCreateDto;
import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderDto;
import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderUpdateDto;
import com.example.bank_account_manager.infrastructure.rest.exception.AccountHolderNotFoundException;
import com.example.bank_account_manager.persistence.accountholder.AccountHolder;
import com.example.bank_account_manager.persistence.accountholder.AccountHolderMapper;
import com.example.bank_account_manager.persistence.accountholder.AccountHolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountHolderService {

    private static final String HOLDER_NOT_FOUND = "Account holder with id %d not found";

    private final AccountHolderRepository repository;
    private final AccountHolderMapper mapper;

    @Transactional(readOnly = true)
    public List<AccountHolderDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public AccountHolderDto findById(Integer id) {
        AccountHolder holder = repository.findById(id)
                .orElseThrow(() -> new AccountHolderNotFoundException(HOLDER_NOT_FOUND.formatted(id)));
        return mapper.toDto(holder);
    }

    public AccountHolderDto create(AccountHolderCreateDto dto) {
        AccountHolder entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        AccountHolder saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    public AccountHolderDto update(Integer id, AccountHolderUpdateDto dto) {
        AccountHolder existing = repository.findById(id)
                .orElseThrow(() -> new AccountHolderNotFoundException(HOLDER_NOT_FOUND.formatted(id)));
        mapper.updateEntity(dto, existing);
        AccountHolder saved = repository.save(existing);
        return mapper.toDto(saved);
    }

    public void delete(Integer id) {
        AccountHolder existing = repository.findById(id)
                .orElseThrow(() -> new AccountHolderNotFoundException(HOLDER_NOT_FOUND.formatted(id)));
        repository.delete(existing);
    }
}
