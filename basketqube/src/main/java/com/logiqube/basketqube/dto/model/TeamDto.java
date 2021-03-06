package com.logiqube.basketqube.dto.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TeamDto {
	
	private long teamId;
	private String leagueCode;
	private String leagueName;
	private String teamCode;
	private String teamName;
	private String imgUrl;

	public TeamDto(String leagueCode, String leagueName, String teamCode, String teamName) {
		this.leagueCode = leagueCode;
		this.leagueName = leagueName;
		this.teamCode = teamCode;
		this.teamName = teamName;
	}

	public TeamDto(String teamName) {
		this.teamName = teamName;
	}

	public TeamDto() {
	}
	
	
	
	

}
