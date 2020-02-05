package com.logiqube.basketqube;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BasketqubeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BasketqubeApplication.class, args);
		System.out.println("App started...");
	}

}
