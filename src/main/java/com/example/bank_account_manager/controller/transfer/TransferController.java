package com.example.bank_account_manager.controller.transfer;

import com.example.bank_account_manager.controller.transfer.dto.TransferCreateDto;
import com.example.bank_account_manager.controller.transfer.dto.TransferDto;
import com.example.bank_account_manager.infrastructure.rest.error.ApiError;
import com.example.bank_account_manager.service.transfer.TransferService;
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
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
@Tag(name = "Transfers", description = "Operations for transferring money between accounts")
public class TransferController {

    private final TransferService transferService;

    @GetMapping
    @Operation(summary = "List all transfers")
    @ApiResponse(responseCode = "200", description = "Transfers fetched successfully")
    public ResponseEntity<List<TransferDto>> getAllTransfers() {
        return ResponseEntity.ok(transferService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transfer by ID")
    @ApiResponse(responseCode = "200", description = "Transfer found")
    @ApiResponse(responseCode = "404", description = "Transfer not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<TransferDto> getTransferById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(transferService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new transfer")
    @ApiResponse(responseCode = "201", description = "Transfer created")
    @ApiResponse(responseCode = "400", description = "Business rule or validation error",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<TransferDto> createTransfer(@Valid @RequestBody TransferCreateDto dto) {
        TransferDto created = transferService.create(dto);
        URI location = URI.create("/api/v1/transfers/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }
}
