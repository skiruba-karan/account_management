package com.example.account_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

@SpringBootApplication
public class AccountManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountManagementSystemApplication.class, args);
	}

}
