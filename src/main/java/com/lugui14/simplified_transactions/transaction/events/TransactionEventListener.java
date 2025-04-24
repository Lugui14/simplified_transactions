package com.lugui14.simplified_transactions.transaction.events;

import com.lugui14.simplified_transactions.notification.domain.NotificationDto;
import com.lugui14.simplified_transactions.notification.services.NotificationService;
import com.lugui14.simplified_transactions.transaction.services.TransactionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TransactionEventListener {

    private final TransactionService transactionService;
    private final NotificationService notificationService;

    public TransactionEventListener(TransactionService transactionService, NotificationService notificationService) {
        this.transactionService = transactionService;
        this.notificationService = notificationService;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTransactionCreatedEvent(TransactionCreatedEvent event) {
        transactionService.processTransaction(event.transactionId());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTransactionCreatedEvent(TransactionConfirmedEvent event) {
        notificationService.sendNotification(
                new NotificationDto(event.userId(), "Your transaction with id " + event.transactionId() + " has been confirmed.")
        );
    }
}
