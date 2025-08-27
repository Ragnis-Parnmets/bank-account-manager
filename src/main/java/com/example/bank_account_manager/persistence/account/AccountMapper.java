package com.example.bank_account_manager.persistence.account;

import com.example.bank_account_manager.controller.account.dto.AccountCreateDto;
import com.example.bank_account_manager.controller.account.dto.AccountDto;
import com.example.bank_account_manager.controller.account.dto.AccountUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Mapper(componentModel = "spring", uses = com.example.bank_account_manager.infrastructure.time.TimeConfig.class)
public interface AccountMapper {

    @Mapping(source = "accountHolder.id", target = "accountHolderId")
    @Mapping(source = "accountType.id", target = "accountTypeId")
    @Mapping(target = "accountHolder", expression = "java(fullName(account.getAccountHolder().getFirstName(), account.getAccountHolder().getLastName()))")
    @Mapping(target = "accountType", source = "accountType.typeName")
    @Mapping(target = "balance", expression = "java(formatBalance(account.getBalance()))")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToDateTime")
    AccountDto toDto(Account account);

    // Map basic fields from create DTO to entity; relations handled in service
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountHolder", ignore = true)
    @Mapping(target = "accountType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Account toEntity(AccountCreateDto dto);

    // Update basic fields; relations handled in service
    @Mapping(target = "accountHolder", ignore = true)
    @Mapping(target = "accountType", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(AccountUpdateDto dto, @MappingTarget Account account);

    // Helpers
    default String fullName(String firstName, String lastName) {
        if (firstName == null && lastName == null) return null;
        if (firstName == null) return lastName;
        if (lastName == null) return firstName;
        return firstName + " " + lastName;
    }

    default String formatBalance(BigDecimal amount) {
        if (amount == null) return null;
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
        DecimalFormat df = new DecimalFormat("0.00", symbols);
        df.setGroupingUsed(false);
        return df.format(amount) + " €";
    }
}
