package com.lugui14.simplified_transactions.transaction.controllers;

import com.lugui14.simplified_transactions.transaction.domain.Transaction;
import com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionDto;
import com.lugui14.simplified_transactions.transaction.services.TransactionService;
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
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<Page<Transaction>> getTransactionHistory(@PathVariable Integer userId, Pageable pageable) {
        return ResponseEntity.ok(transactionService.transactionHistory(userId, pageable));
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionDto));
    }

}
