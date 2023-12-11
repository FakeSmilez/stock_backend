package com.codemobiles.stock_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.codemobiles.stock_backend.service.StorageService;

@SpringBootApplication
public class StockBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(StockBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return args -> {
			storageService.init();
		};
	}
}
