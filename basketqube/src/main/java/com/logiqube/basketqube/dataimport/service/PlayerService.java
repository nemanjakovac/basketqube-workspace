package com.logiqube.basketqube.dataimport.service;

import java.util.List;

import com.logiqube.basketqube.dto.model.PlayerDto;

public interface PlayerService {
	
	PlayerDto savePlayer(PlayerDto playerDto);
	PlayerDto updatePlayer(PlayerDto playerDto);
	
	PlayerDto getByFirstNameAndLastName(String firstName, String lastName);
	
	List<PlayerDto> getAll();
	PlayerDto findById(String id);

}
