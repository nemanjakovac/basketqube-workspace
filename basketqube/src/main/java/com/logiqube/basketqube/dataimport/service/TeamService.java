package com.logiqube.basketqube.dataimport.service;

import com.logiqube.basketqube.dto.model.TeamDto;

public interface TeamService {
	
	TeamDto saveTeam(TeamDto teamDto);
	TeamDto updateTeam(TeamDto teamDto);

}
