package com.logiqube.basketqube.dataimport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logiqube.basketqube.dataimport.repository.TeamRepository;
import com.logiqube.basketqube.dto.mapper.TeamMapper;
import com.logiqube.basketqube.dto.model.TeamDto;
import com.logiqube.basketqube.model.Team;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamRepository teamRepository;

	@Override
	public TeamDto saveTeam(TeamDto teamDto) {
		Team team = teamRepository.findByLeagueCodeAndTeamCode(teamDto.getLeagueCode(), teamDto.getTeamCode());
		if (team == null) {
			team = TeamMapper.toTeamEntity(teamDto);
			return TeamMapper.toTeamDto(teamRepository.save(team));

		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamDto updateTeam(TeamDto teamDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
