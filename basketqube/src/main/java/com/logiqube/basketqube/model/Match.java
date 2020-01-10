package com.logiqube.basketqube.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

@Data
@Document(collection = "match")
public class Match implements Serializable {

	private static final long serialVersionUID = 3455908450482620919L;

	@Field(value = "league_code")
	private String leagueCode;

	@Field(value = "league_name")
	private String leagueName;

	@Field(value = "season_code")
	private String seasonCode;

	@Field(value = "date")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime date;

	@Field(value = "round")
	private String round;
	
	@Field(value = "match_time")
	private int matchTime;	
	
	@Field(value = "home_team")
	private TeamScore homeTeam;
	
	@Field(value = "away_team")
	private TeamScore awayTeam;

	@Field(value = "box_score")
	private BoxScore boxScore;
}