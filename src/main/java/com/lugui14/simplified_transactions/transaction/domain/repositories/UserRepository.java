package com.lugui14.simplified_transactions.transaction.domain.repositories;

import com.lugui14.simplified_transactions.transaction.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
