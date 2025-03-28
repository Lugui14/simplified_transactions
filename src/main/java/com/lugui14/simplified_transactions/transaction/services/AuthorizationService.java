package com.lugui14.simplified_transactions.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.dtos.AuthorizationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Service
public class AuthorizationService {

    private final RestTemplate restTemplate;

    @Value("${mock.transaction_authorization.url}")
    private String transactionAuthorizationURL;

    public AuthorizationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public boolean isTransactionAuthorized() {
        try {
            ResponseEntity<AuthorizationDto> response = restTemplate.getForEntity(
                    transactionAuthorizationURL,
                    AuthorizationDto.class
            );

            return Objects.nonNull(response.getBody()) && response.getBody().authorized();
        } catch (RestClientException e) {
            throw new RuntimeException("Transaction is not authorized");
        }
    }
}
