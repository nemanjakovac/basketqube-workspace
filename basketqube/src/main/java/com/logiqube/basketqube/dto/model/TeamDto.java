package com.logiqube.basketqube.dto.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TeamDto {
	
	private String leagueCode;
	
	private String leagueName;
	
	private String teamCode;
	
	private String teamName;

	public TeamDto(String leagueCode, String leagueName, String teamCode, String teamName) {
		this.leagueCode = leagueCode;
		this.leagueName = leagueName;
		this.teamCode = teamCode;
		this.teamName = teamName;
	}

}
