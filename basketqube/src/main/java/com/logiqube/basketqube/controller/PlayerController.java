package com.logiqube.basketqube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logiqube.basketqube.dataimport.service.PlayerService;
import com.logiqube.basketqube.dto.model.PlayerDto;

@CrossOrigin
@RestController
@RequestMapping(value = "/player")
public class PlayerController {

	@Autowired
	PlayerService playerService;

	@GetMapping(value = "")
	public ResponseEntity<List<PlayerDto>> findAll() {
		return new ResponseEntity<>(playerService.getAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PlayerDto> findTeamById(@PathVariable("id") String id) {
		return new ResponseEntity<>(playerService.findById(id), HttpStatus.OK);
	}

}
