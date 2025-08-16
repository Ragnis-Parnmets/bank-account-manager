package com.example.bank_account_manager.controller.account;

import com.example.bank_account_manager.controller.account.dto.AccountCreateDto;
import com.example.bank_account_manager.controller.account.dto.AccountDto;
import com.example.bank_account_manager.controller.account.dto.AccountUpdateDto;
import com.example.bank_account_manager.service.account.AccountService;
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
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Operations for managing bank accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @Operation(summary = "List all accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Integer id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new account")
    @ApiResponse(responseCode = "201", description = "Account created")
    @ApiResponse(responseCode = "409", description = "Account already exists. Account number must be unique.",
            content = @Content(schema = @Schema(implementation = com.example.bank_account_manager.infrastructure.rest.error.ApiError.class)))
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountCreateDto dto) {
        AccountDto created = accountService.create(dto);
        URI location = URI.create("/accounts/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Integer id, @Valid @RequestBody AccountUpdateDto dto) {
        AccountDto updated = accountService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account by ID",
            description = "Deletes the account and automatically removes its related transfers (ON DELETE CASCADE). " +
                    "On success returns 204 No Content. "
    )
    @ApiResponse(responseCode = "204", description = "Account deleted")
    @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
