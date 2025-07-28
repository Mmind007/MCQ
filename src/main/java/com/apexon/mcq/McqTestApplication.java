package com.apexon.mcq;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class McqTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(McqTestApplication.class, args);
	}

}
