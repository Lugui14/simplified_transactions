package com.lugui14.simplified_transactions.transaction.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionConfirmedEvent extends ApplicationEvent {

    private final Integer userId;
    private final Integer transactionId;

    public TransactionConfirmedEvent(Object source, Integer userId, Integer transactionId) {
        super(source);
        this.userId = userId;
        this.transactionId = transactionId;
    }
}
