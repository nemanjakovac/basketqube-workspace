package com.logiqube.basketqube.dataimport.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logiqube.basketqube.dataimport.repository.PlayerRepository;
import com.logiqube.basketqube.dto.mapper.PlayerMapper;
import com.logiqube.basketqube.dto.model.PlayerDto;
import com.logiqube.basketqube.model.Player;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public PlayerDto savePlayer(PlayerDto playerDto) {
		Player player = playerRepository.findByFirstNameAndLastName(playerDto.getFirstName(), playerDto.getLastName());
		if(player == null) {
			player = PlayerMapper.toPlayerEntity(playerDto);
			return PlayerMapper.toPlayerDto(playerRepository.save(player));
		}
		
//		throw new Exception();
		log.error("Player duplicate");
		return null;
	}

	@Override
	public PlayerDto updatePlayer(PlayerDto playerDto) {
		Optional<Player> player = Optional.ofNullable(playerRepository.findByFirstNameAndLastName(playerDto.getFirstName(), playerDto.getLastName()));
		if(player.isPresent()) {
			Player playerModel = player.get();
			playerModel.setNationality(playerDto.getNationality());
			playerModel.setNote(playerDto.getNote());
			return PlayerMapper.toPlayerDto(playerRepository.save(playerModel));
		}
//		throw new Exception();
		//TODO handle exception
		log.error("Player with this height doesn't exist");
		return null;
	}

	@Override
	public PlayerDto getByFirstNameAndLastName(String firstName, String lastName) {
		Optional<Player> player = Optional.ofNullable(playerRepository.findByFirstNameAndLastName(firstName, lastName));
		if(player.isPresent()) {
			Player playerModel = player.get();
			return PlayerMapper.toPlayerDto(playerModel);
		} else {
			return null;
		}
	}
	
	

}
