package com.logiqube.basketqube.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class TeamLeagueData implements Serializable {
	
	private static final long serialVersionUID = 2359903528350427493L;

	@Field(value = "league_code")
	private String leagueCode;
	
	@Field(value = "league_name")
	private String leagueName;
	
	@Field(value = "team_code")
	private String teamCode;
	
	@Field(value = "team_name")
	private String teamName;

	public TeamLeagueData(String leagueCode, String leagueName, String teamCode, String teamName) {
		this.leagueCode = leagueCode;
		this.leagueName = leagueName;
		this.teamCode = teamCode;
		this.teamName = teamName;
	}

}
