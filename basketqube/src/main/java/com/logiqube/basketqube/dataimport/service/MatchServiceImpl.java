package com.logiqube.basketqube.dataimport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logiqube.basketqube.dataimport.repository.MatchRepository;
import com.logiqube.basketqube.model.Match;

@Service
public class MatchServiceImpl implements MatchService {
	
	@Autowired
	MatchRepository matchRepository;

	@Override
	public Match saveMatch(Match match) {
		return matchRepository.save(match);
	}

}
