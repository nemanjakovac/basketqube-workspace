package com.logiqube.basketqube.dto.model;

import java.time.LocalDateTime;

import com.logiqube.basketqube.model.BoxScore;
import com.logiqube.basketqube.model.TeamScore;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MatchDto {

	private String leagueCode;
	private String leagueName;
	private String seasonCode;
	private LocalDateTime date;
	private String round;
	private int matchTime;
	private TeamScore homeTeam;
	private TeamScore awayTeam;
	private BoxScore boxScore;

}
