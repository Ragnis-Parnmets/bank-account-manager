package com.example.bank_account_manager.controller.account;

import com.example.bank_account_manager.controller.account.dto.AccountCreateDto;
import com.example.bank_account_manager.controller.account.dto.AccountDto;
import com.example.bank_account_manager.controller.account.dto.AccountUpdateDto;
import com.example.bank_account_manager.infrastructure.rest.error.ApiError;
import com.example.bank_account_manager.service.account.AccountService;
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
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Operations for managing bank accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @Operation(summary = "List all accounts")
    @ApiResponse(responseCode = "200", description = "Accounts fetched successfully")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID")
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<AccountDto> getAccountById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new account")
    @ApiResponse(responseCode = "201", description = "Account created")
    @ApiResponse(responseCode = "400", description = "Validation error",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "409", description = "Account already exists. Account number must be unique.",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountCreateDto dto) {
        AccountDto created = accountService.create(dto);
        URI location = URI.create("/api/v1/accounts/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account")
    @ApiResponse(responseCode = "200", description = "Account updated")
    @ApiResponse(responseCode = "400", description = "Validation error",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "404", description = "Account not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<AccountDto> updateAccount(@PathVariable @Positive Integer id, @Valid @RequestBody AccountUpdateDto dto) {
        AccountDto updated = accountService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account by ID",
            description = "Performs a soft delete by setting status to DELETED. On success returns 204 No Content. ")
    @ApiResponse(responseCode = "204", description = "Account deleted")
    @ApiResponse(responseCode = "404", description = "Account not found",
            content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> deleteAccount(@PathVariable @Positive Integer id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
