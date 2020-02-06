package com.logiqube.basketqube.dataimport.service;

import java.util.List;

import com.logiqube.basketqube.dto.model.TeamDto;

public interface TeamService {
	
	TeamDto saveTeam(TeamDto teamDto);
	TeamDto updateTeam(TeamDto teamDto);
	List<TeamDto> getAll();
	TeamDto findById(String id);

}
