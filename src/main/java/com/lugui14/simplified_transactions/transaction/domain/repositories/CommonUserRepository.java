package com.lugui14.simplified_transactions.transaction.domain.repositories;

import com.lugui14.simplified_transactions.transaction.domain.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonUserRepository extends JpaRepository<CommonUser, Integer> {
}
