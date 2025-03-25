package com.lugui14.simplified_transactions.transaction.domain.dtos;

import java.math.BigDecimal;

public record TransactionDto(BigDecimal value, Integer payer, Integer payee) {
}
