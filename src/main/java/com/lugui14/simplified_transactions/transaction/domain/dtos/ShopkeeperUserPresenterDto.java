package com.lugui14.simplified_transactions.transaction.domain.dtos;


import com.lugui14.simplified_transactions.transaction.domain.ShopkeeperUser;

import java.time.LocalDateTime;

public record ShopkeeperUserPresenterDto(Integer id, String name, String email, String cpf, String type, LocalDateTime createdAt) {
    public ShopkeeperUserPresenterDto(ShopkeeperUser shopkeeperUser) {
        this(
                shopkeeperUser.getId(),
                shopkeeperUser.getName(),
                shopkeeperUser.getEmail(),
                shopkeeperUser.getCpf(),
                "COMMON",
                shopkeeperUser.getCreatedAt()
        );
    }
}
