package com.logiqube.basketqube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping(value = "")
	public ResponseEntity<List<TeamDto>> findAll(){		
		return new ResponseEntity<>(teamService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TeamDto> findTeamById(@PathVariable("id") String id){
		return new ResponseEntity<>(teamService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "")
	public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
		if(teamDto != null) {
			return new ResponseEntity<>(teamService.createTeam(teamDto), HttpStatus.CREATED); 
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	@PutMapping(value = "")
	public ResponseEntity<TeamDto> updateTeam(@RequestBody TeamDto teamDto) {
		if(teamDto != null) {
			return new ResponseEntity<>(teamService.updateTeam(teamDto), HttpStatus.OK); 
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TeamDto> deleteTeam(@PathVariable("id") String id) {
		teamService.deleteTeam(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	

}
