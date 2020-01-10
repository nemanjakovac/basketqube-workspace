package com.logiqube.basketqube.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class BoxScoreData implements Serializable {
	
	private static final long serialVersionUID = -868889094039649237L;

	@Field(value = "player_id")
	private String playerId;
	
	@Field(value = "player_name")
	private String playerName;
	
	@Field(value = "team_id")
	private String teamId;
	
	@Field(value = "team_name")
	private String teamName;
	
	@Field(value = "minutes")
	private String minutesPlayed;
	
	@Field(value = "points")
	private String points;
	
	@Field(value = "fg_2pt_att")
	private int fieldGoal2ptAtt;
	
	@Field(value = "fg_2pt_made")
	private int fieldGoal2ptMade;
	
	@Field(value = "fg_2pt_pct")
	private String fieldGoal2ptPct;
	
	@Field(value = "fg_3pt_att")
	private int fieldGoal3ptAtt;
	
	@Field(value = "fg_3pt_made")
	private int fieldGoal3ptMade;
	
	@Field(value = "fg_3pt_pct")
	private String fieldGoal3ptPct;
	
	@Field(value = "ft_1pt_att")
	private int freeThrow1ptAtt;
	
	@Field(value = "ft_1pt_made")
	private int freeThrow1ptMade;
	
	@Field(value = "ft_1pt_pct")
	private String freeThrow1ptPct;
	
	@Field(value = "reb_off")
	private int reboundsOff;
	
	@Field(value = "reb_def")
	private int reboundsDef;
	
	@Field(value = "reb_tot")
	private int reboundsTotal;
	
	@Field(value = "assists")
	private int assists;
	
	@Field(value = "steals")
	private int steals;
	
	@Field(value = "turnovers")
	private int turnovers;
	
	@Field(value = "blocks_fv")
	private int blocksFavour;
	
	@Field(value = "blocks_ag")
	private int blocksAgainst;
	
	@Field(value = "fouls_cm")
	private int foulsCommited;
	
	@Field(value = "fouls_rec")
	private int foulsReceived;
	
	@Field(value = "index")
	private int index;
	
}
