package com.lugui14.simplified_transactions.transaction.domain;

import com.lugui14.simplified_transactions.transaction.domain.enums.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id_from", nullable = false)
    @NotNull(message = "Sender user cannot be null")
    private User userFrom;

    @ManyToOne
    @JoinColumn(name = "user_id_to", nullable = false)
    @NotNull(message = "Receiver user cannot be null")
    private User userTo;

    @NotNull(message = "Amount cannot be null")
    @Column(nullable = false)
    private BigDecimal amount;

    @Column()
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
}