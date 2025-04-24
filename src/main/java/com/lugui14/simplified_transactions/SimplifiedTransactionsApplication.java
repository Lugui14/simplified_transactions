package com.lugui14.simplified_transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SimplifiedTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplifiedTransactionsApplication.class, args);
	}

}
