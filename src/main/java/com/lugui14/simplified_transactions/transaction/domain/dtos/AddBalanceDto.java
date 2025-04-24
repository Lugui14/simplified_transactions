package com.lugui14.simplified_transactions.transaction.domain.dtos;

import java.math.BigDecimal;

public record AddBalanceDto(BigDecimal value, Integer userId) {}
