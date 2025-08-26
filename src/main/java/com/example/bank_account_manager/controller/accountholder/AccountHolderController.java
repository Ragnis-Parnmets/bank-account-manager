package com.example.bank_account_manager.controller.accountholder;

import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderCreateDto;
import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderDto;
import com.example.bank_account_manager.controller.accountholder.dto.AccountHolderUpdateDto;
import com.example.bank_account_manager.service.accountholder.AccountHolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/account-holders")
@RequiredArgsConstructor
@Tag(name = "Account holders", description = "Operations for managing account holders")
public class AccountHolderController {

    private final AccountHolderService accountHolderService;

    @GetMapping
    @Operation(summary = "List all account holders")
    public ResponseEntity<List<AccountHolderDto>> getAllAccountHolders() {
        return ResponseEntity.ok(accountHolderService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account holder by ID")
    public ResponseEntity<AccountHolderDto> getAccountHolderById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(accountHolderService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new account holder")
    @ApiResponse(responseCode = "201", description = "Account holder created")
    public ResponseEntity<AccountHolderDto> createAccountHolder(@Valid @RequestBody AccountHolderCreateDto dto) {
        AccountHolderDto created = accountHolderService.create(dto);
        URI location = URI.create("/api/v1/account-holders/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account holder")
    public ResponseEntity<AccountHolderDto> updateAccountHolder(@PathVariable @Positive Integer id, @Valid @RequestBody AccountHolderUpdateDto dto) {
        AccountHolderDto updated = accountHolderService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account holder by ID",
            description = "Performs a soft delete by setting status to DELETED. On success returns 204 No Content. ")
    @ApiResponse(responseCode = "204", description = "Account holder deleted", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Account holder not found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> deleteAccountHolder(@PathVariable @Positive Integer id) {
        accountHolderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
