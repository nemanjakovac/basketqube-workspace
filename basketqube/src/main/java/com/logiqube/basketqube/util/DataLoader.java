package com.logiqube.basketqube.util;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dataimport.repository.PlayerRepository;
import com.logiqube.basketqube.dataimport.repository.TeamRepository;
import com.logiqube.basketqube.dataimport.service.PlayerService;
import com.logiqube.basketqube.dataimport.service.TeamService;
import com.logiqube.basketqube.dto.model.PlayerDto;
import com.logiqube.basketqube.dto.model.TeamDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		cleanExistingData();
//		savePlayer();
//		updatePlayer();
//		saveTeam();
	}

	@Autowired
	PlayerService service;

	@Autowired
	PlayerRepository playerRepository;

	public void savePlayer() {
		PlayerDto playerDto = new PlayerDto("Nemanja", "Kovac", LocalDate.of(1989, 3, 17), "Serbian",
				Double.valueOf("1.83"), "Guard");
		service.savePlayer(playerDto);
		log.info("Player saved");
	}

	public void updatePlayer() {
		PlayerDto playerDto = new PlayerDto("Nemanja", "Kovac", LocalDate.of(1989, 3, 17), "Serbian",
				Double.valueOf("1.83"), "Guard");
		playerDto.setNationality("Zanzibar");
		playerDto.setNote("This is updated player!");
		service.updatePlayer(playerDto);
		log.info("Player updated");
	}

	private void cleanExistingData() {
//		playerRepository.deleteAll();
//		log.info("Player table deleted");
		teamRepository.deleteAll();
		log.info("Team table deleted");
	}
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	TeamRepository teamRepository;
	
	public void saveTeam() {

		TeamDto teamDto = new TeamDto("ZZZ", "Euroleague", "YYY", "Partizan");
		
		TeamDto savedTeam = teamService.saveTeam(teamDto);
		
		log.info("Team saved", savedTeam);
	}
	
}
