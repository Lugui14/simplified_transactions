package com.lugui14.simplified_transactions.transaction.domain.repositories;

import com.lugui14.simplified_transactions.transaction.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Page<Transaction> getTransactionsByUserFromIdOrUserToId(Integer userFromId, Integer userToId, Pageable pageable);
}
