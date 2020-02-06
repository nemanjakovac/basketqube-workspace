package com.logiqube.basketqube.dataimport.service;

import java.util.List;

import com.logiqube.basketqube.dto.model.MatchDto;
import com.logiqube.basketqube.model.Match;

public interface MatchService {
	
	Match saveMatch(Match match);

	List<MatchDto> getAll();

	MatchDto findById(String id);

}
