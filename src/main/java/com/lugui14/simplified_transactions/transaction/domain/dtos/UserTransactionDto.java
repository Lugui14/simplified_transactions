package com.lugui14.simplified_transactions.transaction.domain.dtos;

import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.enums.UserType;

public record UserTransactionDto(Integer id, String name, String email, String cpf, UserType type) {
    public UserTransactionDto(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getType()
        );
    }
}
