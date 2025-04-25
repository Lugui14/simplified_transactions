package com.lugui14.simplified_transactions.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.dtos.AuthorizationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorizationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @InjectMocks
    private AuthorizationService authorizationService;

    private final String mockUrl = "${mock.transaction_authorization.url}";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        authorizationService = new AuthorizationService(restTemplateBuilder);

        try {
            var field = AuthorizationService.class.getDeclaredField("transactionAuthorizationURL");
            field.setAccessible(true);
            field.set(authorizationService, mockUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set mock URL", e);
        }
    }

    @Test
    @DisplayName("Should return true if transaction is authorized")
    void shouldReturnTrueIfTransactionAuthorized() {
        AuthorizationDto dto = new AuthorizationDto(true);
        ResponseEntity<AuthorizationDto> response = new ResponseEntity<>(dto, HttpStatus.OK);

        when(restTemplate.getForEntity(mockUrl, AuthorizationDto.class)).thenReturn(response);

        boolean result = authorizationService.isTransactionAuthorized();

        assertTrue(result);
        verify(restTemplate).getForEntity(mockUrl, AuthorizationDto.class);
    }

    @Test
    @DisplayName("Should return false if response body is null")
    void shouldReturnFalseIfResponseBodyIsNull() {
        ResponseEntity<AuthorizationDto> response = new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.getForEntity(mockUrl, AuthorizationDto.class)).thenReturn(response);

        boolean result = authorizationService.isTransactionAuthorized();

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false if not authorized")
    void shouldReturnFalseIfNotAuthorized() {
        AuthorizationDto dto = new AuthorizationDto(false);
        ResponseEntity<AuthorizationDto> response = new ResponseEntity<>(dto, HttpStatus.OK);

        when(restTemplate.getForEntity(mockUrl, AuthorizationDto.class)).thenReturn(response);

        boolean result = authorizationService.isTransactionAuthorized();

        assertFalse(result);
    }

    @Test
    @DisplayName("Should throw exception if RestClientException is thrown")
    void shouldThrowExceptionIfRestClientFails() {
        when(restTemplate.getForEntity(mockUrl, AuthorizationDto.class))
                .thenThrow(new RestClientException("Connection error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authorizationService.isTransactionAuthorized();
        });

        assertEquals("Transaction is not authorized", exception.getMessage());
    }
}