package com.lugui14.simplified_transactions.transaction.domain.repositories;

import com.lugui14.simplified_transactions.transaction.domain.Transaction;
import com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("select new com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionHistoryDto(t) " +
            "from Transaction t " +
            "where t.userFrom.id = :userFromId or t.userTo.id = :userToId ")
    Page<TransactionHistoryDto> getTransactionsByUserFromIdOrUserToId(@Param("userFromId") Integer userFromId, @Param("userToId") Integer userToId, Pageable pageable);
}
