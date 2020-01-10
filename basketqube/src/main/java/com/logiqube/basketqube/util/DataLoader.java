package com.logiqube.basketqube.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dataimport.repository.MatchRepository;
import com.logiqube.basketqube.dataimport.repository.PlayerRepository;
import com.logiqube.basketqube.dataimport.repository.TeamRepository;
import com.logiqube.basketqube.dataimport.service.PlayerService;
import com.logiqube.basketqube.dataimport.service.TeamService;
import com.logiqube.basketqube.dto.model.PlayerDto;
import com.logiqube.basketqube.dto.model.TeamDto;
import com.logiqube.basketqube.model.BoxScore;
import com.logiqube.basketqube.model.BoxScoreData;
import com.logiqube.basketqube.model.Match;
import com.logiqube.basketqube.model.TeamScore;

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
//		saveMatch();
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
	
	@Autowired
	MatchRepository matchRepository;
	
	public void saveMatch() {
		Match match = new Match();
		
		match.setLeagueCode("EL");
		match.setLeagueName("Euroleague");
		match.setRound("1");
		match.setSeasonCode("2019");
		match.setDate(LocalDateTime.now());
		match.setMatchTime(40);
		
		TeamScore homeTeamScore = new TeamScore();
		
		homeTeamScore.setTeamId("BER");
		homeTeamScore.setTeamName("ALBA Berlin");
		homeTeamScore.setWinner(true);
		homeTeamScore.setTotal(73);
		homeTeamScore.setTotal1q(13);
		homeTeamScore.setTotal2q(22);
		homeTeamScore.setTotal3q(15);
		homeTeamScore.setTotal4q(23);
		
		TeamScore awayTeamScore = new TeamScore();
		
		awayTeamScore.setTeamId("MAD");
		awayTeamScore.setTeamName("Real Madrid");
		awayTeamScore.setWinner(true);
		awayTeamScore.setTotal(98);
		awayTeamScore.setTotal1q(21);
		awayTeamScore.setTotal2q(22);
		awayTeamScore.setTotal3q(25);
		awayTeamScore.setTotal4q(30);
		
		match.setHomeTeam(homeTeamScore);
		match.setAwayTeam(awayTeamScore);
		
		BoxScore boxScore = new BoxScore();
		
		BoxScoreData homeTeamData = new BoxScoreData();
		homeTeamData.setTeamId("BER");
		homeTeamData.setTeamName("ALBA Berlin");
		homeTeamData.setMinutesPlayed("400:00");
		homeTeamData.setFieldGoal2ptAtt(32);
		homeTeamData.setFieldGoal2ptMade(21);
		
		boxScore.setHomeTeamBoxScoreData(homeTeamData);
		
		BoxScoreData awayTeamData = new BoxScoreData();
		awayTeamData.setTeamId("MAD");
		awayTeamData.setTeamName("Real Madrid");
		awayTeamData.setMinutesPlayed("400:00");
		awayTeamData.setFieldGoal2ptAtt(41);
		awayTeamData.setFieldGoal2ptMade(26);
		
		boxScore.setAwayTeamBoxScoreData(awayTeamData);
		
		Set<BoxScoreData> homePlayerData = new HashSet<>();
		
		BoxScoreData homePlayer1Data = new BoxScoreData();
		homePlayer1Data.setPlayerId("0009345");
		homePlayer1Data.setPlayerName("Nemanja Kovac");
		homePlayer1Data.setMinutesPlayed("38:23");
		homePlayer1Data.setFieldGoal2ptAtt(6);
		homePlayer1Data.setFieldGoal2ptMade(4);
		homePlayer1Data.setAssists(3);
		
		homePlayerData.add(homePlayer1Data);
		
		BoxScoreData homePlayer2Data = new BoxScoreData();
		homePlayer2Data.setPlayerId("0009355");
		homePlayer2Data.setPlayerName("Petar Petrovic");
		homePlayer2Data.setMinutesPlayed("26:13");
		homePlayer2Data.setFieldGoal2ptAtt(4);
		homePlayer2Data.setFieldGoal2ptMade(1);
		homePlayer2Data.setAssists(5);
		
		homePlayerData.add(homePlayer2Data);
		
		boxScore.setHomePlayersBoxScoreData(homePlayerData);
		
		Set<BoxScoreData> awayPlayerData = new HashSet<>();
		
		BoxScoreData awayPlayer1Data = new BoxScoreData();
		awayPlayer1Data.setPlayerId("03546345");
		awayPlayer1Data.setPlayerName("Jovan Jovanovic");
		awayPlayer1Data.setMinutesPlayed("38:23");
		awayPlayer1Data.setFieldGoal2ptAtt(6);
		awayPlayer1Data.setFieldGoal2ptMade(4);
		awayPlayer1Data.setAssists(3);
		
		awayPlayerData.add(awayPlayer1Data);
		
		BoxScoreData awayPlayer2Data = new BoxScoreData();
		awayPlayer2Data.setPlayerId("0009377");
		awayPlayer2Data.setPlayerName("Milos Milosevic");
		awayPlayer2Data.setMinutesPlayed("26:13");
		awayPlayer2Data.setFieldGoal2ptAtt(4);
		awayPlayer2Data.setFieldGoal2ptMade(1);
		awayPlayer2Data.setAssists(5);
		
		awayPlayerData.add(awayPlayer2Data);
		
		boxScore.setAwayPlayersBoxScoreData(awayPlayerData);
		
		match.setBoxScore(boxScore);
		
		matchRepository.save(match);
	}
	
}
