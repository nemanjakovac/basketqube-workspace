package com.logiqube.basketqube.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/healthCheck")
public class AppHealthCheckController {
	
	@GetMapping(value = "")
	public ResponseEntity<String> getAppStatus(){
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

}
