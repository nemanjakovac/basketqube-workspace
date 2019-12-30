package com.logiqube.basketqube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.logiqube.basketqube.dataimport.service.PlayerService;
import com.logiqube.basketqube.model.Player;

@SpringBootApplication
public class BasketqubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasketqubeApplication.class, args);
		System.out.println("App started...");
	}

}
