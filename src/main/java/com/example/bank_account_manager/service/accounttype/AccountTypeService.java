package com.example.bank_account_manager.service.accounttype;

import com.example.bank_account_manager.controller.accounttype.dto.AccountTypeCreateDto;
import com.example.bank_account_manager.controller.accounttype.dto.AccountTypeDto;
import com.example.bank_account_manager.controller.accounttype.dto.AccountTypeUpdateDto;
import com.example.bank_account_manager.infrastructure.rest.exception.AccountTypeNotFoundException;
import com.example.bank_account_manager.persistence.accounttype.AccountType;
import com.example.bank_account_manager.persistence.accounttype.AccountTypeMapper;
import com.example.bank_account_manager.persistence.accounttype.AccountTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountTypeService {

    private static final String TYPE_NOT_FOUND = "Account type with id %d not found";
    private static final String ACTIVE = "ACTIVE";

    private final AccountTypeRepository repository;
    private final AccountTypeMapper mapper;

    @Transactional(readOnly = true)
    public List<AccountTypeDto> findAll() {
        return repository.findAllByStatus(ACTIVE).stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public AccountTypeDto findById(Integer id) {
        AccountType type = repository.findByIdAndStatus(id, ACTIVE)
                .orElseThrow(() -> new AccountTypeNotFoundException(TYPE_NOT_FOUND.formatted(id)));
        return mapper.toDto(type);
    }

    public AccountTypeDto create(AccountTypeCreateDto dto) {
        AccountType entity = mapper.toEntity(dto);
        entity.setStatus(ACTIVE);
        AccountType saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    public AccountTypeDto update(Integer id, AccountTypeUpdateDto dto) {
        AccountType existing = repository.findByIdAndStatus(id, ACTIVE)
                .orElseThrow(() -> new AccountTypeNotFoundException(TYPE_NOT_FOUND.formatted(id)));
        mapper.updateEntity(dto, existing);
        AccountType saved = repository.save(existing);
        return mapper.toDto(saved);
    }

    public void delete(Integer id) {
        AccountType existing = repository.findByIdAndStatus(id, ACTIVE)
                .orElseThrow(() -> new AccountTypeNotFoundException(TYPE_NOT_FOUND.formatted(id)));
        existing.setStatus("DELETED");
        repository.save(existing);
    }
}
