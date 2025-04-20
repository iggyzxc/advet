package com.macadev.advet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // Exclude security
public class AdvetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvetApplication.class, args);
	}

}
