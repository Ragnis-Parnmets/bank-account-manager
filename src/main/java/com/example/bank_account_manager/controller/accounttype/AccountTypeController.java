package com.example.bank_account_manager.controller.accounttype;

import com.example.bank_account_manager.controller.accounttype.dto.AccountTypeCreateDto;
import com.example.bank_account_manager.controller.accounttype.dto.AccountTypeDto;
import com.example.bank_account_manager.controller.accounttype.dto.AccountTypeUpdateDto;
import com.example.bank_account_manager.service.accounttype.AccountTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/account-types")
@RequiredArgsConstructor
@Tag(name = "Account types", description = "Operations for managing account types")
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @GetMapping
    @Operation(summary = "List all account types")
    public ResponseEntity<List<AccountTypeDto>> getAllAccountTypes() {
        return ResponseEntity.ok(accountTypeService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account type by ID")
    public ResponseEntity<AccountTypeDto> getAccountTypeById(@PathVariable Integer id) {
        return ResponseEntity.ok(accountTypeService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new account type")
    @ApiResponse(responseCode = "201", description = "Account type created")
    public ResponseEntity<AccountTypeDto> createAccountType(@Valid @RequestBody AccountTypeCreateDto dto) {
        AccountTypeDto created = accountTypeService.create(dto);
        URI location = URI.create("/account-types/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account type")
    public ResponseEntity<AccountTypeDto> updateAccountType(@PathVariable Integer id, @Valid @RequestBody AccountTypeUpdateDto dto) {
        AccountTypeDto updated = accountTypeService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account type by ID",
            description = "Performs a soft delete by setting status to DELETED. On success returns 204 No Content.")
    @ApiResponse(responseCode = "204", description = "Account type deleted", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Account type not found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> deleteAccountType(@PathVariable Integer id) {
        accountTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
