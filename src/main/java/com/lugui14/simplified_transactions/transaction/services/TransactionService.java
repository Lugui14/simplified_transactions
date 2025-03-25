package com.lugui14.simplified_transactions.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.Transaction;
import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionDto;
import com.lugui14.simplified_transactions.transaction.domain.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public TransactionService(
            TransactionRepository transactionRepository,
            UserService userService
    ) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    @Transactional
    public Transaction createTransaction(TransactionDto dto) {
        User userFrom = userService.findById(dto.payer());
        User userTo = userService.findById(dto.payee());

        Transaction transaction = Transaction.builder()
                .userFrom(userFrom)
                .userTo(userTo)
                .amount(dto.value())
                .createdAt(LocalDateTime.now())
                .build();



        return transactionRepository.save(transaction);
    }
}
