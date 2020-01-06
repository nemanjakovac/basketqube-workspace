package com.logiqube.basketqube.dto.mapper;

import com.logiqube.basketqube.dto.model.TeamDto;
import com.logiqube.basketqube.model.Team;
import com.logiqube.basketqube.model.TeamLeagueData;

public class TeamMapper {

	public static TeamDto toTeamDto(Team team) {

		return new TeamDto(team.getName());
	}

	public static Team toTeamEntity(TeamDto teamDto) {
		Team team = new Team(teamDto.getTeamName());
		TeamLeagueData data = new TeamLeagueData(teamDto.getLeagueCode(), teamDto.getLeagueName(),
				teamDto.getTeamCode(), teamDto.getTeamName());
		team.addLeagueData(data);
		return team;
	}

}
