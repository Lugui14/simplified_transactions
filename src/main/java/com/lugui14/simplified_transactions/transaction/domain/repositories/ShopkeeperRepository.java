package com.lugui14.simplified_transactions.transaction.domain.repositories;

import com.lugui14.simplified_transactions.transaction.domain.ShopkeeperUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopkeeperRepository extends JpaRepository<ShopkeeperUser, Integer> {
}
