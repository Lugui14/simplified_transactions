package com.lugui14.simplified_transactions.transaction.domain.dtos;

import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.enums.UserType;

import java.time.LocalDateTime;

public record UserPresenterDto(Integer id, String name, String email, String cpf, UserType type, LocalDateTime createdAt) {
    public UserPresenterDto(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getType(),
                user.getCreatedAt()
                );
    }
}
