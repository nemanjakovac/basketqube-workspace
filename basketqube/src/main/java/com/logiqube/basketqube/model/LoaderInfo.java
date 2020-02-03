package com.logiqube.basketqube.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

@Data
@Document(collection = "loader_info")
public class LoaderInfo {
	
	@Id
//	@Field(value = "league_code")
	private String leagueCode;
	
	@Field(value = "round")
	private String round;
	
	@Field(value = "last_loaded")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime lastLoaded;

	public LoaderInfo(String leagueCode, String round, LocalDateTime lastLoaded) {
		this.leagueCode = leagueCode;
		this.round = round;
		this.lastLoaded = lastLoaded;
	}
	
	

}
