package com.logiqube.basketqube.dataimport.matches;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dataimport.service.MatchService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoadMatches {

	private String EL_BASE_URL = "https://www.euroleague.net";
	private static String LEAGUE_CODE = "EL";
	private static String SEASON_CURRENT;
	private static String PHASE_CURRENT;
	private static String ROUND_CURRENT;
	private static String ROUND_PARAMETER = "?gamenumber=";
	private static String PHASE_PARAMETER = "&phasetypecode=";
	private static String SEASON_PARAMETER = "&seasoncode=";

	@Autowired
	MatchService matchService;

	public void hello() {
		log.info("Hello load matches");
	}

	public void load() throws IOException {

		Document doc = Jsoup.connect(EL_BASE_URL + "/main/results").get();

		Element roundData = doc.getElementsByClass("round-header").first();

		extractCurrentData(roundData);
		
		// get minimum round from DB max, so we only load next rounds
		for (int i = 1; i < 2; i++) {
			String roundPage = EL_BASE_URL.
					concat(ROUND_PARAMETER).concat(String.valueOf(i))
					.concat(PHASE_PARAMETER).concat(PHASE_CURRENT)
					.concat(SEASON_PARAMETER).concat(SEASON_CURRENT);
			
			Document roundDoc = Jsoup.connect(roundPage).get();
			
			
//			"div.wp-module-asidegames"
//			"div.livescore"
			Element matchList = roundDoc.getElementsByClass("livescore").last();
			
//			Elements matches = matchList.select("a");
			
			String a = "";
			
			
		}



	}

	private static synchronized void extractCurrentData(Element roundData) {
		SEASON_CURRENT = extractCurrentSeason(roundData.select("div > span:nth-child(1)").text());
		PHASE_CURRENT = extractCurrentPhase(roundData.select("div > span:nth-child(2)").text());
		ROUND_CURRENT = extractCurrentRound(roundData.select("div > span:nth-child(3)").text());

	}

	private static String extractCurrentSeason(String season) {
		season = season.substring(0, 4);
		season = "E".concat(season);
		return season;
	}

	private static String extractCurrentPhase(String phase) {
		switch (phase) {
		case "Regular Season":
			phase = "RS";
			break;
		case "Playoffs":
			phase = "PO";
			break;
		case "Final Four":
			phase = "FF";
			break;
		default:
			phase = "RS";
			break;
		}
		return phase;
	}

	private static String extractCurrentRound(String round) {
		round = round.substring(round.indexOf(' ')).trim();
		return round;
	}

}
