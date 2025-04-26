package com.lugui14.simplified_transactions.transaction.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionCreatedEvent extends ApplicationEvent {
    private final Integer transactionId;

    public TransactionCreatedEvent(Object source, Integer transactionId) {
        super(source);
        this.transactionId = transactionId;
    }
}
