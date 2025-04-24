package com.lugui14.simplified_transactions.transaction.controllers;

import com.lugui14.simplified_transactions.transaction.domain.Transaction;
import com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionDto;
import com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionHistoryDto;
import com.lugui14.simplified_transactions.transaction.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transactions", description = "Transaction management operations")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/history/{userId}")
    @Operation(summary = "Get transaction history", description = "This endpoint retrieves the transaction history for a user")
    public ResponseEntity<Page<TransactionHistoryDto>> getTransactionHistory(@PathVariable Integer userId, Pageable pageable) {
        return ResponseEntity.ok(transactionService.transactionHistory(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by id", description = "This endpoint retrieves a transaction by its id")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Integer id) {
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create transaction", description = "This endpoint creates a new transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionDto));
    }

}
