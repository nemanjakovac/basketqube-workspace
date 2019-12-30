package com.logiqube.basketqube.dataimport.scheduler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dataimport.players.LoadPlayers;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@PropertySource("classpath:scheduler.properties")
@Slf4j
public class Scheduler {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "${load.players.schedule}")
	public void reportCurrentTime() {
		if (log.isInfoEnabled())
			log.info("Current time = {}", dateFormat.format(new Date()));
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			
		}
		
		if (log.isInfoEnabled())
			log.info("Woke up after sleep");
		
		
	}

	@Autowired
	LoadPlayers loadPlayers;

//	@Scheduled(cron = "${load.players.schedule}")
	public void callLoadPlayers() {
		try {
			loadPlayers.load();
		} catch (IOException e) {
			// TODO handle exception
		}
	}
}
