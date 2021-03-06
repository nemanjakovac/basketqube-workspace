package com.logiqube.basketqube.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Data;

@Data
//@ApiModel(description = "Person basic info", value = "Person")
public class Person {
	
//	@Id
	@Field(value = "person_id")
	private String personId;
	
	@Field(value = "first_name")
	private String firstName;
	
	@Field(value = "last_name;")
	private String lastName;
	
	@Field(value = "birth_date")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate birthDate;
	
	@Field(value = "nationality")
	private String nationality;
	
	@Field(value = "notes")
	private String notes;

	public Person(String firstName, String lastName, LocalDate birthDate, String nationality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.nationality = nationality;
	}
	
	

}
