package com.logiqube.basketqube.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Data;

//@SuppressWarnings("serial")
@Data
@Document(collection = "player")
public final class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Transient
	public static final String SEQUENCE_NAME = "player_sequence";

	@Id
//	@Field(value = "player_id") - must not give custom name to id field
	private long playerId;
	
	@Field(value = "first_name")
	private String firstName;
	
	@Field(value = "last_name")
	private String lastName;
	
	@Field(value = "birth_date")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate birthDate;
	
	@Field(value = "nationality")
	private String nationality;
	
	@Field(value = "height")
	private double height;

	@Field(value = "position")
	private String position;
	
	@Field(value = "note")
	private String note;
	
	@Field(value = "img_url")
	private String imgUrl;

	public Player(String firstName, String lastName, LocalDate birthDate, String nationality, double height,
			String position) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.nationality = nationality;
		this.height = height;
		this.position = position;
	}
	
	public void changeNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

	public Player() {
	}

}
