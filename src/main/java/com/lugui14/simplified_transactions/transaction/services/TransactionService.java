package com.lugui14.simplified_transactions.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.Transaction;
import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionDto;
import com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionHistoryDto;
import com.lugui14.simplified_transactions.transaction.domain.enums.TransactionStatus;
import com.lugui14.simplified_transactions.transaction.domain.enums.UserType;
import com.lugui14.simplified_transactions.transaction.domain.repositories.TransactionRepository;
import com.lugui14.simplified_transactions.transaction.events.TransactionConfirmedEvent;
import com.lugui14.simplified_transactions.transaction.events.TransactionCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private ApplicationEventPublisher eventPublisher;

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final AuthorizationService authorizationService;

    public TransactionService(
            TransactionRepository transactionRepository,
            UserService userService,
            AuthorizationService authorizationService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.eventPublisher = eventPublisher;
    }

    public Page<TransactionHistoryDto> transactionHistory(Integer userId, Pageable pageable) {
        return transactionRepository.getTransactionsByUserFromIdOrUserToId(userId, userId, pageable);
    }

    public Transaction findById(Integer id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find transaction with id " + id));
    }

    @Transactional
    public Transaction createTransaction(TransactionDto dto) {
        User userFrom = userService.findById(dto.payer());
        User userTo = userService.findById(dto.payee());

        if(userFrom.getType().equals(UserType.SHOPKEEPER))
            throw new RuntimeException("Payer cannot be a shopkeeper");

        Transaction transaction = Transaction.builder()
                .userFrom(userFrom)
                .userTo(userTo)
                .amount(dto.value())
                .createdAt(LocalDateTime.now())
                .status(TransactionStatus.PENDING)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        eventPublisher.publishEvent(new TransactionCreatedEvent(savedTransaction.getId()));

        return savedTransaction;
    }

    @Transactional
    public void processTransaction(Integer transactionId) {
        Transaction transaction = this.findById(transactionId);

        if(!authorizationService.isTransactionAuthorized()) {
            transaction.setStatus(TransactionStatus.REJECTED);
            return;
        }

        userService.subtractBalance(transaction.getUserFrom().getId(), transaction.getAmount());
        userService.addBalance(transaction.getUserTo().getId(), transaction.getAmount());

        transaction.setStatus(TransactionStatus.APPROVED);
        transactionRepository.save(transaction);

        eventPublisher.publishEvent(new TransactionConfirmedEvent(transaction.getUserTo().getId(), transactionId));
    }
}
