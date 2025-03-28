package com.lugui14.simplified_transactions.transaction.domain.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionDto(
        @NotNull(message = "Inconsistent data, value is null") BigDecimal value,
        @NotNull(message = "Inconsistent data, payer is null") Integer payer,
        @NotNull(message = "Inconsistent data, payee is null") Integer payee
) {
}
