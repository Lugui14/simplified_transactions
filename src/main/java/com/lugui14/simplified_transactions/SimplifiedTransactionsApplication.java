package com.lugui14.simplified_transactions;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@OpenAPIDefinition(info=@Info(title="Simplified Transactions"))
@SpringBootApplication
public class SimplifiedTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplifiedTransactionsApplication.class, args);
	}

}
