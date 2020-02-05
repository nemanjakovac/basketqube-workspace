package com.logiqube.basketqube.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dto.model.MatchDto;
import com.logiqube.basketqube.model.Match;

@Component
public class MatchMapper {

	@Autowired
	ModelMapper modelMapper;

	public MatchDto convertToDto(Match match) {
		MatchDto matchDto = modelMapper.map(match, MatchDto.class);
		return matchDto;
	}

	public Match convertToEntity(MatchDto matchDto) {
		Match match = modelMapper.map(matchDto, Match.class);
		return match;
	}

}
