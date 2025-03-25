package com.lugui14.simplified_transactions.transaction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "User name cannot be empty")
    private String name;

    @Column(length = 11, unique = true, nullable = false)
    @NotBlank(message = "User cpf cannot be empty")
    @Size(min = 11, max = 11, message = "User cpf must have 11 digits")
    private String cpf;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "User email cannot be empty")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "User password cannot be empty")
    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();

}
