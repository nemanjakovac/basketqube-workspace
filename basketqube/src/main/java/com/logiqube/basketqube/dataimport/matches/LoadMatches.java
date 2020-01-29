package com.logiqube.basketqube.dataimport.matches;

import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dataimport.matches.factory.MatchLoader;
import com.logiqube.basketqube.enums.Enums.League;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoadMatches {

//	private MatchLoader loader;

	public void hello() {
		log.info("Hello load matches");
	}

	// use synchronized only one thread is active
	public synchronized void load() {

		MatchLoader loader = MatchLoader.getMatchLoader(League.EL);
		loader.loadAllMatches();

	}

}
