package com.logiqube.basketqube.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dto.model.TeamDto;
import com.logiqube.basketqube.model.Team;
import com.logiqube.basketqube.model.TeamLeagueData;

@Component
public class TeamMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public TeamDto convertToDto(Team team) {
		TeamDto teamDto = modelMapper.map(team, TeamDto.class);
//		teamDto.setLeagueCode(team.getLeagueData().toString());
		return teamDto;

//		return new TeamDto(team.getName());
	}

	public Team convertToEntity(TeamDto teamDto) {
		Team team = modelMapper.map(teamDto, Team.class);
		
		TeamLeagueData data = new TeamLeagueData(teamDto.getLeagueCode(), teamDto.getLeagueName(),
		teamDto.getTeamCode(), teamDto.getTeamName());
		team.addLeagueData(data);
		
		return team;
		
//		Team team = new Team(teamDto.getTeamName());
//		TeamLeagueData data = new TeamLeagueData(teamDto.getLeagueCode(), teamDto.getLeagueName(),
//				teamDto.getTeamCode(), teamDto.getTeamName());
//		team.addLeagueData(data);
//		return team;
	}

}
