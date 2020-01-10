package com.logiqube.basketqube.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
public class BoxScore implements Serializable {
	
	private static final long serialVersionUID = -2512486402165071428L;

	@Field(value = "home_team")
	private BoxScoreData homeTeamBoxScoreData;
	
	@Field(value = "away_team")
	private BoxScoreData awayTeamBoxScoreData;
	
	@JsonDeserialize(as=LinkedHashSet.class)
	@Field(value = "home_players")
	private Set<BoxScoreData> homePlayersBoxScoreData;
	
	@JsonDeserialize(as=LinkedHashSet.class)
	@Field(value = "away_players")
	private Set<BoxScoreData> awayPlayersBoxScoreData;
	
}
