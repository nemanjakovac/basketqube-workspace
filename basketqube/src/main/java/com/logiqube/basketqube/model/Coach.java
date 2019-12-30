package com.logiqube.basketqube.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Document(collection = "coach")
@EqualsAndHashCode(callSuper = true)
public final class Coach extends Person {

	public Coach(String firstName, String lastName, LocalDate birthDate, String nationality) {
		super(firstName, lastName, birthDate, nationality);
	}

}
