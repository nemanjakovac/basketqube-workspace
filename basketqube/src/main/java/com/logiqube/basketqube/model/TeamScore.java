package com.logiqube.basketqube.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class TeamScore implements Serializable {

	private static final long serialVersionUID = -5711748934026138750L;

	@Field(value = "team_id")
	private String teamId;

	@Field(value = "team_name")
	private String teamName;

	@Field(value = "winner")
	private boolean winner;

	@Field(value = "total")
	private int total;

	@Field(value = "total_1q")
	private int total1q;

	@Field(value = "total_2q")
	private int total2q;

	@Field(value = "total_3q")
	private int total3q;

	@Field(value = "total_4q")
	private int total4q;

	@Field(value = "total_ot")
	private int totalOt;
}
