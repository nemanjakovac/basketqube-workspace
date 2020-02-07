package com.logiqube.basketqube.dataimport.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logiqube.basketqube.dataimport.repository.TeamRepository;
import com.logiqube.basketqube.dto.mapper.TeamMapper;
import com.logiqube.basketqube.dto.model.TeamDto;
import com.logiqube.basketqube.model.Player;
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
		Optional<Team> team = teamRepository.findByLeagueCodeAndTeamCode(teamDto.getLeagueCode(), teamDto.getTeamCode());
		if (!team.isPresent()) {
			return teamMapper.convertToDto(teamRepository.save(teamMapper.convertToEntity(teamDto)));
		} else {
			if (log.isDebugEnabled())
				log.debug("Team already exists");
			return teamDto;
		}
	}

	@Override
	public TeamDto updateTeam(TeamDto teamDto) {
		Team updatedTeam = teamRepository.save(teamMapper.convertToEntity(teamDto));
		return teamMapper.convertToDto(updatedTeam);
	}

	@Override
	public List<TeamDto> getAll() {
		return teamRepository.findAll().stream().map(teamMapper::convertToDto).collect(Collectors.toList());
	}

	@Override
	public TeamDto findById(String id) {
		Optional<Team> team = teamRepository.findByTeamId(Long.valueOf(id));
		if(team.isPresent())
			return teamMapper.convertToDto(team.get());
		//TODO handle object not present
		return null;
	}

	@Override
	public TeamDto createTeam(TeamDto teamDto) {
		Team team = teamRepository.save(teamMapper.convertToEntity(teamDto));
		return teamMapper.convertToDto(team);
	}

	@Override
	public void deleteTeam(String teamId) {
		Optional<Team> team = teamRepository.findByTeamId(Long.valueOf(teamId));
		if(team.isPresent())
			teamRepository.delete(team.get());
	}

}
