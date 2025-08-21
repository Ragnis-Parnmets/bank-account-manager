package com.example.bank_account_manager.persistence.accounttype;

import com.example.bank_account_manager.controller.accounttype.dto.AccountTypeCreateDto;
import com.example.bank_account_manager.controller.accounttype.dto.AccountTypeDto;
import com.example.bank_account_manager.controller.accounttype.dto.AccountTypeUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {

    AccountTypeDto toDto(AccountType entity);

    List<AccountTypeDto> toDtoList(List<AccountType> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    AccountType toEntity(AccountTypeCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(AccountTypeUpdateDto dto, @MappingTarget AccountType entity);
}
