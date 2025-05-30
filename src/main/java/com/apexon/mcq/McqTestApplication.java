package com.apexon.mcq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class McqTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(McqTestApplication.class, args);
	}

}
