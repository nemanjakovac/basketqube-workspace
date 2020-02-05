package com.logiqube.basketqube.dto.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)  // https://projectlombok.org/features/experimental/Accessors
public class PlayerDto {
	
//	private String personId;
	
	private String firstName;
	
	private String lastName;
	
//	@JsonSerialize(using = LocalDateSerializer.class)
//	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate birthDate;
	
	private String nationality;
	
	private double height;

	private String position;
	
	private String note;

	public PlayerDto(String firstName, String lastName, LocalDate birthDate, String nationality, double height,
			String position) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.nationality = nationality;
		this.height = height;
		this.position = position;
	}

	public PlayerDto(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public PlayerDto() {
	}
	
	

}
