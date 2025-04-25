package com.lugui14.simplified_transactions.unit.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.enums.UserType;
import com.lugui14.simplified_transactions.transaction.domain.repositories.UserRepository;
import com.lugui14.simplified_transactions.transaction.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return user by id")
    void shouldReturnUserById() {
        User user = User.builder()
                .id(1)
                .name("John Doe")
                .email("john@email.com")
                .password("password")
                .cpf("12345678912")
                .type(UserType.COMMON)
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.findById(1);

        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("Should throw exception when user doesn't exist")
    void shouldThrowExceptionWhenUserDoesntExists() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.findById(1));
        assertEquals("Could not find user with id 1", exception.getMessage());
    }

    @Test
    @DisplayName("Should create a new user")
    void shouldCreateANewUser() {
        User user = User.builder()
                .name("John Doe")
                .email("john@email.com")
                .password("password")
                .cpf("12345678912")
                .type(UserType.COMMON)
                .balance(BigDecimal.valueOf(100.0))
                .build();

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.create(user);

        assertSame(user, result);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should add balance to user and return user")
    void addBalanceFunctionShouldReturnUser() {
        User user = User.builder()
                .id(1)
                .name("John Doe")
                .email("john@email.com")
                .password("password")
                .cpf("12345678912")
                .type(UserType.COMMON)
                .balance(BigDecimal.valueOf(50))
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        User result = userService.rechargeValue(1, BigDecimal.valueOf(30));

        assertEquals(BigDecimal.valueOf(80), result.getBalance());
    }

    @Test
    @DisplayName("Should throw exception if try to recharge a non-existent user")
    void shouldThrowExceptionIfTryRechargeNonExistentUser() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.rechargeValue(1, BigDecimal.TEN));
        assertEquals("Could not find common user with id 1", exception.getMessage());
    }

    @Test
    @DisplayName("Should subtract balance")
    void shouldSubtractBalance() {
        User user = User.builder()
                .id(1)
                .name("John Doe")
                .email("john@email.com")
                .password("password")
                .cpf("12345678912")
                .type(UserType.COMMON)
                .balance(BigDecimal.valueOf(100))
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        userService.subtractBalance(1, BigDecimal.valueOf(40));

        assertEquals(BigDecimal.valueOf(60), user.getBalance());
    }

    @Test
    @DisplayName("Should throw exception if try to subtract more than balance")
    void shouldThrowExceptionIfSubtractInsufficientBalance() {
        User user = User.builder()
                .id(1)
                .name("John Doe")
                .email("john@email.com")
                .password("password")
                .cpf("12345678912")
                .type(UserType.COMMON)
                .balance(BigDecimal.valueOf(20))
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.subtractBalance(1, BigDecimal.valueOf(30)));
        assertEquals("User balance cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception if subtract balance of inexistent user")
    void shouldThrowErrorIfSubtractBalanceOfNonExistentUser() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.subtractBalance(1, BigDecimal.TEN));
        assertEquals("Could not find common user with id 1", exception.getMessage());
    }
}
