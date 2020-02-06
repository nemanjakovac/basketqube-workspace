package com.logiqube.basketqube.dataimport.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logiqube.basketqube.dataimport.repository.MatchRepository;
import com.logiqube.basketqube.dto.mapper.MatchMapper;
import com.logiqube.basketqube.dto.model.MatchDto;
import com.logiqube.basketqube.model.Match;
import com.logiqube.basketqube.model.Player;

@Service
public class MatchServiceImpl implements MatchService {
	
	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	private MatchMapper matchMapper;

	@Override
	public Match saveMatch(Match match) {
		return matchRepository.save(match);
	}

	@Override
	public List<MatchDto> getAll() {
		return matchRepository.findAll().stream().map(matchMapper::convertToDto).collect(Collectors.toList());
	}

	@Override
	public MatchDto findById(String id) {
		Optional<Match> match = matchRepository.findByMatchId(Long.valueOf(id));
		if(match.isPresent())
			return matchMapper.convertToDto(match.get());
		//TODO handle object not present
		return null;
	}

}
