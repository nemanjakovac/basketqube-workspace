package com.logiqube.basketqube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logiqube.basketqube.dataimport.service.TeamService;
import com.logiqube.basketqube.dto.model.TeamDto;

@CrossOrigin
@RestController
@RequestMapping (value = "/team")
public class TeamController {
	
	@Autowired
	TeamService teamService;
	
//	@GetMapping(value = "")
//	public ResponseEntity<List<TeamDto>> findAll(){
//		return new ResponseEntity<>(teamService.getAll(), HttpStatus.OK);
//	}
	
	@GetMapping(value = "")
	public ResponseEntity<List<TeamDto>> findAll(){
	//	List<Team> teams = teamService.getAllTeams();
	//	return teams.stream().map(mapper)(this:convertToDto).col
		
		return new ResponseEntity<>(teamService.getAll(), HttpStatus.OK);
	}

}
