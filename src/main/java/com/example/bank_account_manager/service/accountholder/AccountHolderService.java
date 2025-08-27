package com.example.bank_account_manager.service.accountholder;

import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderCreateDto;
import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderDto;
import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderUpdateDto;
import com.example.bank_account_manager.infrastructure.rest.exception.AccountHolderNotFoundException;
import com.example.bank_account_manager.persistence.accountholder.AccountHolder;
import com.example.bank_account_manager.persistence.accountholder.AccountHolderMapper;
import com.example.bank_account_manager.persistence.accountholder.AccountHolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountHolderService {

    private static final String HOLDER_NOT_FOUND = "Account holder with id %d not found";
    private static final String ACTIVE = "ACTIVE";

    private final AccountHolderRepository repository;
    private final AccountHolderMapper mapper;

    @Cacheable("accountHolders")
    @Transactional(readOnly = true)
    public List<AccountHolderDto> findAll() {
        return repository.findAllByStatus(ACTIVE).stream().map(mapper::toDto).toList();
    }

    @Cacheable(cacheNames = "accountHolderById", key = "#id")
    @Transactional(readOnly = true)
    public AccountHolderDto findById(Integer id) {
        AccountHolder holder = repository.findByIdAndStatus(id, ACTIVE)
                .orElseThrow(() -> new AccountHolderNotFoundException(HOLDER_NOT_FOUND.formatted(id)));
        return mapper.toDto(holder);
    }

    @CacheEvict(cacheNames = {"accountHolders", "accountHolderById"}, allEntries = true)
    public AccountHolderDto create(AccountHolderCreateDto dto) {
        AccountHolder entity = mapper.toEntity(dto);
        entity.setCreatedAt(Instant.now());
        entity.setStatus(ACTIVE);
        AccountHolder saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @CacheEvict(cacheNames = {"accountHolders", "accountHolderById"}, allEntries = true)
    public AccountHolderDto update(Integer id, AccountHolderUpdateDto dto) {
        AccountHolder existing = repository.findByIdAndStatus(id, ACTIVE)
                .orElseThrow(() -> new AccountHolderNotFoundException(HOLDER_NOT_FOUND.formatted(id)));
        mapper.updateEntity(dto, existing);
        AccountHolder saved = repository.save(existing);
        return mapper.toDto(saved);
    }

    @CacheEvict(cacheNames = {"accountHolders", "accountHolderById"}, allEntries = true)
    public void delete(Integer id) {
        AccountHolder existing = repository.findByIdAndStatus(id, ACTIVE)
                .orElseThrow(() -> new AccountHolderNotFoundException(HOLDER_NOT_FOUND.formatted(id)));
        existing.setStatus("DELETED");
        repository.save(existing);
    }
}
