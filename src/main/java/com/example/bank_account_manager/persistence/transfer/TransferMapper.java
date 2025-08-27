package com.example.bank_account_manager.persistence.transfer;

import com.example.bank_account_manager.controller.transfer.dto.TransferCreateDto;
import com.example.bank_account_manager.controller.transfer.dto.TransferDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = com.example.bank_account_manager.infrastructure.time.TimeConfig.class)
public interface TransferMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToDateTime")
    TransferDto toDto(Transfer transfer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Transfer toEntity(TransferCreateDto dto);
}
