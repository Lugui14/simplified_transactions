package com.lugui14.simplified_transactions.unit.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.Transaction;
import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionDto;
import com.lugui14.simplified_transactions.transaction.domain.dtos.TransactionHistoryDto;
import com.lugui14.simplified_transactions.transaction.domain.enums.TransactionStatus;
import com.lugui14.simplified_transactions.transaction.domain.enums.UserType;
import com.lugui14.simplified_transactions.transaction.domain.repositories.TransactionRepository;
import com.lugui14.simplified_transactions.transaction.events.TransactionConfirmedEvent;
import com.lugui14.simplified_transactions.transaction.events.TransactionCreatedEvent;
import com.lugui14.simplified_transactions.transaction.services.AuthorizationService;
import com.lugui14.simplified_transactions.transaction.services.TransactionService;
import com.lugui14.simplified_transactions.transaction.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return transaction history for user")
    void shouldReturnTransactionHistory() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<TransactionHistoryDto> expectedPage = new PageImpl<>(List.of());
        when(transactionRepository.getTransactionsByUserFromIdOrUserToId(1, 1, pageable)).thenReturn(expectedPage);

        Page<TransactionHistoryDto> result = transactionService.transactionHistory(1, pageable);

        assertEquals(expectedPage, result);
    }

    @Test
    @DisplayName("Should find transaction by id")
    void shouldFindTransactionById() {
        Transaction transaction = Transaction.builder().id(1).build();
        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        Transaction result = transactionService.findById(1);

        assertSame(transaction, result);
    }

    @Test
    @DisplayName("Should throw exception when transaction not found")
    void shouldThrowExceptionWhenTransactionNotFound() {
        when(transactionRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> transactionService.findById(1));
        assertEquals("Could not find transaction with id 1", exception.getMessage());
    }

    @Test
    @DisplayName("Should create a transaction and publish event")
    void shouldCreateTransaction() {
        User payer = User.builder().id(1).type(UserType.COMMON).build();
        User payee = User.builder().id(2).type(UserType.SHOPKEEPER).build();

        TransactionDto dto = new TransactionDto(BigDecimal.valueOf(50), payer.getId(), payee.getId());

        Transaction transaction = Transaction.builder()
                .userFrom(payer)
                .userTo(payee)
                .amount(dto.value())
                .createdAt(LocalDateTime.now())
                .status(TransactionStatus.PENDING)
                .build();

        when(userService.findById(payer.getId())).thenReturn(payer);
        when(userService.findById(payee.getId())).thenReturn(payee);
        when(transactionRepository.save(any())).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(dto);

        assertEquals(TransactionStatus.PENDING, result.getStatus());
        verify(eventPublisher).publishEvent(any(TransactionCreatedEvent.class));
    }

    @Test
    @DisplayName("Should throw exception when payer is shopkeeper")
    void shouldThrowExceptionWhenPayerIsShopkeeper() {
        User payer = User.builder().id(1).type(UserType.SHOPKEEPER).build();
        User payee = User.builder().id(2).type(UserType.COMMON).build();

        TransactionDto dto = new TransactionDto(BigDecimal.valueOf(50), payer.getId(), payee.getId());

        when(userService.findById(payer.getId())).thenReturn(payer);
        when(userService.findById(payee.getId())).thenReturn(payee);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> transactionService.createTransaction(dto));
        assertEquals("Payer cannot be a shopkeeper", exception.getMessage());
    }

    @Test
    @DisplayName("Should process transaction when authorized")
    void shouldProcessTransactionWhenAuthorized() {
        User payer = User.builder().id(1).build();
        User payee = User.builder().id(2).build();

        Transaction transaction = Transaction.builder()
                .id(10)
                .userFrom(payer)
                .userTo(payee)
                .amount(BigDecimal.valueOf(30))
                .status(TransactionStatus.PENDING)
                .build();

        when(transactionRepository.findById(10)).thenReturn(Optional.of(transaction));
        when(authorizationService.isTransactionAuthorized()).thenReturn(true);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        transactionService.processTransaction(10);

        assertEquals(TransactionStatus.APPROVED, transaction.getStatus());
        verify(userService).subtractBalance(payer.getId(), transaction.getAmount());
        verify(userService).rechargeValue(payee.getId(), transaction.getAmount());
        verify(eventPublisher).publishEvent(any(TransactionConfirmedEvent.class));
    }

    @Test
    @DisplayName("Should reject transaction when not authorized")
    void shouldRejectTransactionWhenNotAuthorized() {
        User payer = User.builder().id(1).build();
        User payee = User.builder().id(2).build();

        Transaction transaction = Transaction.builder()
                .id(10)
                .userFrom(payer)
                .userTo(payee)
                .amount(BigDecimal.valueOf(30))
                .status(TransactionStatus.PENDING)
                .build();

        when(transactionRepository.findById(10)).thenReturn(Optional.of(transaction));
        when(authorizationService.isTransactionAuthorized()).thenReturn(false);

        transactionService.processTransaction(10);

        assertEquals(TransactionStatus.REJECTED, transaction.getStatus());
        verify(userService, never()).subtractBalance(anyInt(), any());
        verify(userService, never()).rechargeValue(anyInt(), any());
        verify(eventPublisher, never()).publishEvent(isA(TransactionConfirmedEvent.class));
    }
}