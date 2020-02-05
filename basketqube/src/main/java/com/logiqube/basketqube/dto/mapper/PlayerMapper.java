package com.logiqube.basketqube.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dto.model.PlayerDto;
import com.logiqube.basketqube.model.Player;

// MapStruct
@Component
public class PlayerMapper {
	
	@Autowired
	ModelMapper modelMapper;

	public PlayerDto convertToDto(Player player) {
		PlayerDto playerDto = modelMapper.map(player, PlayerDto.class);
//		playerDto.setNote("test");
		return playerDto;
		
//		return new PlayerDto(player.getFirstName(), player.getLastName(), player.getBirthDate(),
//				player.getNationality(), player.getHeight(), player.getPosition());
	}

	public Player convertToEntity(PlayerDto playerDto) {
		return new Player(playerDto.getFirstName(), playerDto.getLastName(), playerDto.getBirthDate(),
				playerDto.getNationality(), playerDto.getHeight(), playerDto.getPosition());
	}

//	private PlayerMapper() {
//		throw new IllegalStateException("Mapper class");
//	}

}
