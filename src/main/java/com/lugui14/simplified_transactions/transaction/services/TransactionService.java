package com.lugui14.simplified_transactions.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.Transaction;
import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionDto;
import com.lugui14.simplified_transactions.transaction.domain.enums.UserType;
import com.lugui14.simplified_transactions.transaction.domain.repositories.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final AuthorizationService authorizationService;

    public TransactionService(
            TransactionRepository transactionRepository,
            UserService userService,
            AuthorizationService authorizationService
    ) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    public Page<Transaction> transactionHistory(Integer userId, Pageable pageable) {
        return transactionRepository.getTransactionsByUserFromIdOrUserToId(userId, userId, pageable);
    }

    @Transactional
    public Transaction createTransaction(TransactionDto dto) {
        User userFrom = userService.findById(dto.payer());
        User userTo = userService.findById(dto.payee());

        if(userFrom.getType().equals(UserType.SHOPKEEPER))
            throw new RuntimeException("Payer cannot be a shopkeeper");

        if(!authorizationService.isTransactionAuthorized())
            throw new RuntimeException("Transaction is not authorized");

        userService.subtractBalance(userFrom.getId(), dto.value());
        userService.addBalance(userTo.getId(), dto.value());

        Transaction transaction = Transaction.builder()
                .userFrom(userFrom)
                .userTo(userTo)
                .amount(dto.value())
                .createdAt(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }
}
