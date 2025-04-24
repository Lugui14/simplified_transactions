package com.lugui14.simplified_transactions.infrastructure.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocumentationConfig {

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simplified Transactions API")
                        .version("1.0.0")
                        .description("Api used to simulate financial transactions.")
                        .contact(new Contact()
                                .name("Luiz Guilherme Zanella Lopes")
                                .email("zanelallopes9977@gmail.com"))
                );
    }

}
