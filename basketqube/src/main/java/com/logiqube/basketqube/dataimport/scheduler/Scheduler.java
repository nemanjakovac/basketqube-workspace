package com.logiqube.basketqube.dataimport.scheduler;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dataimport.matches.LoadMatches;
import com.logiqube.basketqube.dataimport.players.LoadPlayers;
import com.logiqube.basketqube.dataimport.teams.LoadTeams;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@PropertySource("classpath:scheduler.properties")
@Slf4j
public class Scheduler {

	@Autowired
	LoadPlayers loadPlayers;
	
	@Autowired
	LoadTeams loadTeams;
	
	@Autowired
	LoadMatches loadMatches;

//	@Scheduled(cron = "${load.players.schedule}")
	public void callLoadPlayers() {
		try {
			loadPlayers.load();
		} catch (IOException e) {
			// TODO handle exception
		}
	}
	
//	@Scheduled(cron = "${load.teams.schedule}")
	public void callLoadTeams() {
		try {
			loadTeams.load();
		} catch (IOException e) {
			// TODO handle exception
		}
	}
	
//	@Scheduled(cron = "${load.matches.schedule}")
	public void callLoadMatches() {
		loadMatches.load();
	}
	
}
