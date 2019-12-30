package com.logiqube.basketqube.dto.mapper;

import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dto.model.PlayerDto;
import com.logiqube.basketqube.model.Player;

// MapStruct
@Component
public class PlayerMapper {

	public static PlayerDto toPlayerDto(Player player) {
		return new PlayerDto(player.getFirstName(), player.getLastName(), player.getBirthDate(),
				player.getNationality(), player.getHeight(), player.getPosition());
	}

	public static Player toPlayerEntity(PlayerDto playerDto) {
		return new Player(playerDto.getFirstName(), playerDto.getLastName(), playerDto.getBirthDate(),
				playerDto.getNationality(), playerDto.getHeight(), playerDto.getPosition());
	}

//	private PlayerMapper() {
//		throw new IllegalStateException("Mapper class");
//	}

}
