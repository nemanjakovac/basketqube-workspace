package com.logiqube.basketqube.dataimport.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	@Autowired
	private PlayerMapper playerMapper;

	@Override
	public PlayerDto savePlayer(PlayerDto playerDto) {
		Optional<Player> player = playerRepository.findByFirstNameAndLastName(playerDto.getFirstName(), playerDto.getLastName());
		if(!player.isPresent()) {
			return playerMapper.convertToDto(playerRepository.save(playerMapper.convertToEntity(playerDto)));
		}
//		throw new Exception();
		log.error("Player duplicate");
		return null;
	}

	@Override
	public PlayerDto updatePlayer(PlayerDto playerDto) {
		Optional<Player> player = (playerRepository.findByFirstNameAndLastName(playerDto.getFirstName(), playerDto.getLastName()));
		if(player.isPresent()) {
			Player playerModel = player.get();
			playerModel.setNationality(playerDto.getNationality());
			playerModel.setNote(playerDto.getNote());
			return playerMapper.convertToDto(playerRepository.save(playerModel));
		}
//		throw new Exception();
		//TODO handle exception
		log.error("Player with this height doesn't exist");
		return null;
	}

	@Override
	public PlayerDto getByFirstNameAndLastName(String firstName, String lastName) {
		Optional<Player> player = (playerRepository.findByFirstNameAndLastName(firstName, lastName));
		if(player.isPresent()) {
			Player playerModel = player.get();
			return playerMapper.convertToDto(playerModel);
		} else {
			return null;
		}
	}

	@Override
	public List<PlayerDto> getAll() {
		return playerRepository.findAll().stream().map(playerMapper::convertToDto).collect(Collectors.toList());
	}

	@Override
	public PlayerDto findById(String id) {
		Optional<Player> player = playerRepository.findByPlayerId(Long.valueOf(id));
		if(player.isPresent())
			return playerMapper.convertToDto(player.get());
		//TODO handle object not present
		return null;
	}

	@Override
	public PlayerDto createPlayer(PlayerDto playerDto) {
		Player player = playerRepository.save(playerMapper.convertToEntity(playerDto));
		return playerMapper.convertToDto(player);
	}
	
	

}
