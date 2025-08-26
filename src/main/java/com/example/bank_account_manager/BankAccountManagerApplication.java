package com.example.bank_account_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BankAccountManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccountManagerApplication.class, args);
	}

}
