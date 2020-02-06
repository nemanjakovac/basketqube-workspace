package com.logiqube.basketqube.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
@Document(collection = "team")
public final class Team implements Serializable {

	private static final long serialVersionUID = -2790853358988489440L;
	
	@Transient
	public static final String SEQUENCE_NAME = "team_sequence";

	@Id
	private long teamId;
	
	@Field(value = "name")
	private String teamName;
	
	@JsonDeserialize(as=LinkedHashSet.class)
	@Field(value = "league_data")
	private Set<TeamLeagueData> leagueData = new HashSet<>();
	
	@Field(value = "img_url")
	private String imgUrl;
	
	public void addLeagueData(TeamLeagueData teamLeagueData) {
		this.leagueData.add(teamLeagueData);
	}

	public Team(String name) {
		this.teamName = name;
		this.leagueData = new LinkedHashSet<>();
	}

	public Team() {
	}

}
