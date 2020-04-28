package com.logiqube.basketqube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logiqube.basketqube.dataimport.service.MatchService;
import com.logiqube.basketqube.dto.model.MatchDto;

@CrossOrigin
@RestController
@RequestMapping(value = "/match")
public class MatchController {
	
	@Autowired
	MatchService matchService;
	
	@GetMapping(value = "")
	public ResponseEntity<List<MatchDto>> findAll() {
		return new ResponseEntity<>(matchService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<MatchDto> findTeamById(@PathVariable("id") String id) {
		return new ResponseEntity<>(matchService.findById(id), HttpStatus.OK);
	}
	
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
