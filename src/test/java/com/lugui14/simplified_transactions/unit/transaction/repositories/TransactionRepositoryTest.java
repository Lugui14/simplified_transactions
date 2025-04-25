package com.lugui14.simplified_transactions.unit.transaction.repositories;

import com.lugui14.simplified_transactions.transaction.domain.Transaction;
import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.enums.TransactionStatus;
import com.lugui14.simplified_transactions.transaction.domain.enums.UserType;
import com.lugui14.simplified_transactions.transaction.domain.repositories.TransactionRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        User user = User.builder()
                .name("John Doe")
                .email("john@email.com")
                .password("password")
                .cpf("12345678912")
                .type(UserType.COMMON)
                .balance(BigDecimal.valueOf(100.0))
                .build();

        User user2 = User.builder()
                .name("Jane Doe")
                .email("jane@email.com")
                .password("password")
                .cpf("12345678913")
                .type(UserType.SHOPKEEPER)
                .balance(BigDecimal.valueOf(200.0))
                .build();

        entityManager.persist(user);
        entityManager.persist(user2);

        Transaction transaction = Transaction.builder()
                .userFrom(user)
                .userTo(user2)
                .amount(BigDecimal.valueOf(100.0))
                .status(TransactionStatus.APPROVED)
                .build();

        entityManager.persist(transaction);
    }

    @Test
    @DisplayName("Should return a page of transactions by userId")
    void shouldReturnTransactions() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        var transactions = transactionRepository.getTransactionsByUserFromIdOrUserToId(1, 2, pageRequest);

        assertNotNull(transactions);
        assertEquals(1, transactions.getTotalElements());
        assertEquals(1, transactions.getContent().size());
        assertNotNull(transactions.getContent().get(0).id());
        assertNotNull(transactions.getContent().get(0).payee().id());
        assertNotNull(transactions.getContent().get(0).payer().id());
    }
}