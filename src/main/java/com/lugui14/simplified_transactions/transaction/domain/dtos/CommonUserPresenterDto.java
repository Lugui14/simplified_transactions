package com.lugui14.simplified_transactions.transaction.domain.dtos;

import com.lugui14.simplified_transactions.transaction.domain.CommonUser;

import java.time.LocalDateTime;

public record CommonUserPresenterDto(Integer id, String name, String email, String cpf, String type, LocalDateTime createdAt) {
    public CommonUserPresenterDto(CommonUser commonUser) {
        this(
                commonUser.getId(),
                commonUser.getName(),
                commonUser.getEmail(),
                commonUser.getCpf(),
                "COMMON",
                commonUser.getCreatedAt()
                );
    }
}
