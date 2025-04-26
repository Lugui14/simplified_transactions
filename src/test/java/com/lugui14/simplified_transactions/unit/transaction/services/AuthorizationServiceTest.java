package com.lugui14.simplified_transactions.unit.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.dtos.AuthorizationDto;
import com.lugui14.simplified_transactions.transaction.services.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final String mockUrl = "mock-authorization-service-url";

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
    @DisplayName("Should return true when authorization is approved")
    void shouldReturnTrueWhenAuthorizationIsApproved() {
        AuthorizationDto authorizationDto = new AuthorizationDto(true);
        ResponseEntity<AuthorizationDto> responseEntity = new ResponseEntity<>(authorizationDto, HttpStatus.OK);

        when(restTemplate.getForEntity(mockUrl, AuthorizationDto.class)).thenReturn(responseEntity);

        boolean result = authorizationService.isTransactionAuthorized();

        assertTrue(result);
        verify(restTemplate).getForEntity(mockUrl, AuthorizationDto.class);
    }

    @Test
    @DisplayName("Should return false when authorization is not approved")
    void shouldReturnFalseWhenAuthorizationIsNotApproved() {
        AuthorizationDto authorizationDto = new AuthorizationDto(false);
        ResponseEntity<AuthorizationDto> responseEntity = new ResponseEntity<>(authorizationDto, HttpStatus.OK);

        when(restTemplate.getForEntity(mockUrl, AuthorizationDto.class)).thenReturn(responseEntity);

        boolean result = authorizationService.isTransactionAuthorized();

        assertFalse(result);
        verify(restTemplate).getForEntity(mockUrl, AuthorizationDto.class);
    }

    @Test
    @DisplayName("Should throw exception when RestClientException occurs")
    void shouldThrowExceptionWhenRestClientExceptionOccurs() {
        when(restTemplate.getForEntity(mockUrl, AuthorizationDto.class))
                .thenThrow(new RestClientException("Connection error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authorizationService.isTransactionAuthorized());

        assertEquals("Transaction is not authorized", exception.getMessage());
        verify(restTemplate).getForEntity(mockUrl, AuthorizationDto.class);
    }
}
