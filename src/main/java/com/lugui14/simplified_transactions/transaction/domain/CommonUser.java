package com.lugui14.simplified_transactions.transaction.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("COMMON")
@RequiredArgsConstructor
public class CommonUser extends User {
    public CommonUser(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setCpf(user.getCpf());
        this.setCreatedAt(user.getCreatedAt());
    }
}
