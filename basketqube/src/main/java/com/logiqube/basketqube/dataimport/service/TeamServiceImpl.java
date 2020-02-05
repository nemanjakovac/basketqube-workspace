package com.logiqube.basketqube.dataimport.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logiqube.basketqube.dataimport.repository.TeamRepository;
import com.logiqube.basketqube.dto.mapper.TeamMapper;
import com.logiqube.basketqube.dto.model.TeamDto;
import com.logiqube.basketqube.model.Team;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	TeamMapper teamMapper;

	@Override
	public TeamDto saveTeam(TeamDto teamDto) {
		Team team = teamRepository.findByLeagueCodeAndTeamCode(teamDto.getLeagueCode(), teamDto.getTeamCode());
		if (team == null) {
			team = teamMapper.convertToEntity(teamDto);
			return teamMapper.convertToDto(teamRepository.save(team));
		} else {
			if (log.isDebugEnabled())
				log.debug("Team already exists");
			return teamDto;
		}
	}

	@Override
	public TeamDto updateTeam(TeamDto teamDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamDto> getAll() {
		return teamRepository.findAll().stream().map(teamMapper::convertToDto).collect(Collectors.toList());
	}

}
