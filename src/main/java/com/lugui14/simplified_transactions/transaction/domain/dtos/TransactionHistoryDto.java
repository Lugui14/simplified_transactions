package com.lugui14.simplified_transactions.transaction.domain.dtos;

import com.lugui14.simplified_transactions.transaction.domain.Transaction;
import com.lugui14.simplified_transactions.transaction.domain.enums.TransactionStatus;

import java.math.BigDecimal;

public record TransactionHistoryDto(
        Integer id,
        BigDecimal value,
        UserTransactionDto payer,
        UserTransactionDto payee,
        TransactionStatus status
) {
    public TransactionHistoryDto(Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getAmount(),
                new UserTransactionDto(transaction.getUserFrom()),
                new UserTransactionDto(transaction.getUserTo()),
                transaction.getStatus()
        );
    }
}
