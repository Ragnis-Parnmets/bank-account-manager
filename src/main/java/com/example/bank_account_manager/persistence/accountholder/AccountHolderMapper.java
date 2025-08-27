package com.example.bank_account_manager.persistence.accountholder;

import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderCreateDto;
import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderDto;
import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = com.example.bank_account_manager.infrastructure.time.TimeConfig.class)
public interface AccountHolderMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToDateTime")
    AccountHolderDto toDto(AccountHolder entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AccountHolder toEntity(AccountHolderCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(AccountHolderUpdateDto dto, @MappingTarget AccountHolder entity);
}
