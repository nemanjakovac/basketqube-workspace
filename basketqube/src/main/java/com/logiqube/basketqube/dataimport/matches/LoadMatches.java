package com.logiqube.basketqube.dataimport.matches;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.logiqube.basketqube.dataimport.service.MatchService;
import com.logiqube.basketqube.model.BoxScore;
import com.logiqube.basketqube.model.BoxScoreData;
import com.logiqube.basketqube.model.Match;
import com.logiqube.basketqube.model.TeamScore;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoadMatches {
	
	private enum Entity { TEAM, PLAYER };
	private enum Venue { HOME, AWAY };

	private String EL_BASE_URL = "https://www.euroleague.net";
	private static String LEAGUE_CODE = "EL";
	private static String LEAGUE_NAME = "Euroleague";
	private static String SEASON_MAX;
	private static String PHASE_MAX;
	private static String ROUND_MAX;
	private static String ROUND_CURRENT;
	private static String ROUND_PARAMETER = "?gamenumber=";
	private static String PHASE_PARAMETER = "&phasetypecode=";
	private static String SEASON_PARAMETER = "&seasoncode=";

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, uuuu z: HH:mm");

	@Autowired
	MatchService matchService;

	public void hello() {
		log.info("Hello load matches");
	}

	public void load() throws IOException {

 		Document doc = Jsoup.connect(EL_BASE_URL + "/main/results").get();

		Element roundData = doc.getElementsByClass("round-header").first();

		extractCurrentData(roundData);

		// get minimum round from DB max (last) loaded, so we only load next rounds
		for (int i = 1; i < Integer.valueOf(ROUND_MAX) - 1; i++) {
			ROUND_CURRENT = String.valueOf(i);
			String roundPage = EL_BASE_URL.concat("/main/results")
					.concat(ROUND_PARAMETER).concat(ROUND_CURRENT)
					.concat(PHASE_PARAMETER).concat(PHASE_MAX)
					.concat(SEASON_PARAMETER).concat(SEASON_MAX);

			Document roundDoc = Jsoup.connect(roundPage).get();

//			"div.wp-module-asidegames"
//			"div.livescore"
			Element matchList = roundDoc.getElementsByClass("livescore").last();

			Elements matches = matchList.select("a");

			matches.forEach(match -> {
				String matchUrl = EL_BASE_URL.concat(match.attr("href")).concat("#!boxscore");
				try {
					loadMatch(matchUrl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
				
				try {
//					Thread.sleep(5000);
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO handle exception
					log.debug("Sleeping for 10s");
				}
				
			});

			String a = "";

		}

	}

	private static synchronized void extractCurrentData(Element roundData) {
		SEASON_MAX = extractCurrentSeason(roundData.select("div > span:nth-child(1)").text());
		PHASE_MAX = extractCurrentPhase(roundData.select("div > span:nth-child(2)").text());
		ROUND_MAX = extractCurrentRound(roundData.select("div > span:nth-child(3)").text());

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

	private void loadMatch(String matchUrl) throws IOException {

		Document doc = Jsoup.connect(matchUrl).get();

		Element gameInfo = doc.getElementById("sg-score");



		Match match = createMatch(matchUrl, gameInfo);

		addScoreToMatch(match, gameInfo, doc);
		
		addBoxScoreToMatch(match, doc);
		
		addMatchTime(match);
		
		matchService.saveMatch(match);

		String a = "";

	}

	private Match createMatch(String matchUrl, Element gameInfo) {
		Element gameScore = gameInfo.getElementsByClass("game-score").first();
		Element gameDate = gameInfo.getElementsByClass("dates").first();

		String leagueCode = LEAGUE_CODE;
		String leagueName = LEAGUE_NAME;
		String seasonCode = matchUrl.substring(matchUrl.indexOf("seasoncode") + 12,
				matchUrl.indexOf("seasoncode") + 16);
		String round = ROUND_CURRENT;//matchUrl.substring(matchUrl.indexOf("gamecode") + 9, matchUrl.indexOf("seasoncode") - 1);

		LocalDateTime matchDate = extractMatchDate(gameDate);

		Match match = new Match(leagueCode, leagueName, seasonCode, matchDate, round);

		return match;
	}

	private LocalDateTime extractMatchDate(Element gameDate) {

		String date = gameDate.getElementsByClass("date").first().html();
		return LocalDateTime.parse(date, formatter);
	}

	private void addMatchTime(Match match) {
		String time = match.getBoxScore().getHomeTeamBoxScoreData().getMinutesPlayed();
		int timeInt = Integer.valueOf(time.substring(0, time.indexOf(':')))/5;
		match.setMatchTime(timeInt);
	}

	private void addScoreToMatch(Match match, Element gameInfo, Element doc) {
		
		Element scoreByQuarter = doc.getElementsByClass("PartialsStatsByQuarterContainer").first();

		TeamScore homeTeam = setTeamData(gameInfo, scoreByQuarter, "HOME");

		TeamScore awayTeam = setTeamData(gameInfo, scoreByQuarter, "AWAY");

		match.setHomeTeam(homeTeam);
		
		match.setAwayTeam(awayTeam);

		String abc = "";

	}

	private TeamScore setTeamData(Element gameInfo, Element scoreByQuarter, String venue) {

		Element gameScore = null;
		Elements teamScoreByQuarter = null;
		boolean winner = false;

		switch (venue) {
		case "HOME":
			gameScore = gameInfo.select("div.game-score > div.teams > div.local").first();
			teamScoreByQuarter = scoreByQuarter.select("tr:nth-child(2) > td");
			winner = gameInfo.select("div.game-score > div.teams > div[class=team local winner]").first() != null;
			break;
		case "AWAY":
			gameScore = gameInfo.select("div.game-score > div.teams > div.road").first();
			teamScoreByQuarter = scoreByQuarter.select("tr:nth-child(3) > td");
			winner = gameInfo.select("div.game-score > div.teams > div[class=team road winner]").first() != null;
			break;
		default:
			// TODO throw exception
			break;
		}

		Element teamData = gameScore.getElementsByTag("a").first();

		String teamId = teamData.attr("href");
		teamId = teamId.substring(teamId.indexOf("clubcode") + 9, teamId.indexOf("clubcode") + 12);

		String teamName = teamData.getElementsByClass("name").text();
		String teamTotal = gameScore.select("span.score").text();

		TeamScore team = new TeamScore();

		team.setTeamId(teamId);
		team.setTeamName(teamName);
		team.setWinner(winner);
		team.setTotal(Integer.valueOf(teamTotal));

		team.setTotalsByQuarter(
				Integer.valueOf(teamScoreByQuarter.get(1).text()),
				Integer.valueOf(teamScoreByQuarter.get(2).text()), 
				Integer.valueOf(teamScoreByQuarter.get(3).text()),
				Integer.valueOf(teamScoreByQuarter.get(4).text()));

		if (teamScoreByQuarter.size() > 5) {
			int overtimeTotal = 0;
			for (int i = 5; i < teamScoreByQuarter.size(); i++) {
				overtimeTotal += Integer.valueOf(teamScoreByQuarter.get(i).text());
			}
			team.setTotalOt(overtimeTotal);
		}

		return team;

	}
	

	private void addBoxScoreToMatch(Match match, Element doc) {
		
		BoxScore boxScore = new BoxScore();
		match.setBoxScore(boxScore);
		
		processTeam(match, doc, Venue.HOME);
		processTeam(match, doc, Venue.AWAY);
		
//		Element boxHome = doc.getElementsByClass("LocalClubStatsContainer").first();

//		Element boxAway = doc.getElementsByClass("RoadClubStatsContainer").first();
		
		
		
		
//		BoxScoreData homeTeamBoxScoreData = extractBoxScoreData(match, boxHome, "TEAM", "HOME");
		
		
//		
//		Element boxHomeTeam = boxHome.getElementsByTag("tfoot").first();
//
//		BoxScoreData homeTeamData = extractBoxScoreData(match, boxHomeTeam.getElementsByClass("TotalFooter").first(), Entity.TEAM, Venue.HOME);
//		boxScore.setHomeTeamBoxScoreData(homeTeamData);
		
		
		
//		Element boxHomePlayersContainer = boxHome.getElementsByTag("tbody").first();
//		Elements boxHomePlayers = boxHomePlayersContainer.getElementsByTag("tr");
//		
//		
//		Set<BoxScoreData> homePlayersData = new HashSet<>();
//		BoxScoreData singlePlayerData = null;
//		for (int i = 0; i < boxHomePlayers.size() - 1; i++) {
//			singlePlayerData = extractBoxScoreData(match, boxHomePlayers.get(i), Entity.PLAYER, Venue.HOME);
//			if(singlePlayerData != null)
//				homePlayersData.add(singlePlayerData);
//		}
		
//		boxScore.setHomePlayersBoxScoreData(homePlayersData);
		
//		boxScore.setAwayTeamBoxScoreData(awayTeamBoxScoreData);
		
		String a = "";
	}

	private void processTeam(Match match, Element doc, Venue venue) {
		
		Element box = null;
		
		if(Venue.HOME == venue)
			box = doc.getElementsByClass("LocalClubStatsContainer").first();
		else
			box = doc.getElementsByClass("RoadClubStatsContainer").first();
		
		Element team = box.getElementsByTag("tfoot").first();

		BoxScoreData teamData = extractBoxScoreData(match, team.getElementsByClass("TotalFooter").first(), Entity.TEAM, venue);
		
		Element playersContainer = box.getElementsByTag("tbody").first();
		Elements players = playersContainer.getElementsByTag("tr");
			
		Set<BoxScoreData> playersData = new HashSet<>();
		BoxScoreData singlePlayerData = null;
		for (int i = 0; i < players.size() - 1; i++) {
			singlePlayerData = extractBoxScoreData(match, players.get(i), Entity.PLAYER, venue);
			if(singlePlayerData != null)
				playersData.add(singlePlayerData);
		}
		
		if(Venue.HOME == venue) {
			match.getBoxScore().setHomeTeamBoxScoreData(teamData);
			match.getBoxScore().setHomePlayersBoxScoreData(playersData);
		}
		else {
			match.getBoxScore().setAwayTeamBoxScoreData(teamData);
			match.getBoxScore().setAwayPlayersBoxScoreData(playersData);
		}
		
		
	}

	private BoxScoreData extractBoxScoreData(Match match, Element boxRow, Entity entity, Venue venue) {
		
		Elements stats = boxRow.getElementsByTag("td");
		
		// first div to read in stats row
		int i = 2;
		
		String totalTimePlayed = stats.get(i++).text();
		if ("DNP".equals(totalTimePlayed))
			return null;
		
		String totalScore = stats.get(i++).text();
		String totalFieldGoals2 = stats.get(i++).text();
		String totalFieldGoals3 = stats.get(i++).text();
		String totalFreeThrows = stats.get(i++).text();
		String totalOffensiveRebounds = stats.get(i++).text();
		String totalDefensiveRebounds = stats.get(i++).text();
		String totalTotalRebounds = stats.get(i++).text();
		String totalAssistances = stats.get(i++).text();
		String totalSteals = stats.get(i++).text();
		String totalTurnovers = stats.get(i++).text();
		String totalBlocksFavour = stats.get(i++).text();
		String totalBlocksAgainst = stats.get(i++).text();
		String totalFoulsCommited = stats.get(i++).text();
		String totalFoulsReceived = stats.get(i++).text();
		String totalValuation = stats.get(i).text();
		
		BoxScoreData data = new BoxScoreData();
		
		if (entity == Entity.TEAM) {
			if (venue == Venue.HOME) {
				data.setTeamId(match.getHomeTeam().getTeamId());
				data.setTeamName(match.getHomeTeam().getTeamName());
			} else {
				data.setTeamId(match.getAwayTeam().getTeamId());
				data.setTeamName(match.getAwayTeam().getTeamName());
			}
		} else {
			Element playerInfo = stats.get(1).getElementsByTag("a").first();

			if (playerInfo != null) {
				String playerId = playerInfo.attr("href");
				playerId = playerId.substring(playerId.indexOf("pcode") + 6, playerId.indexOf("seasoncode") - 1);

				data.setPlayerId(playerId);
				data.setPlayerName(playerInfo.text());
//				Element startFive = stats.get(1).select("a[class=PlayerStartFive]").first();
				if (playerInfo.select("a.PlayerStartFive").first() != null)
					data.setStartFive("Y");
			}
		}
		
		data.setMinutesPlayed(totalTimePlayed);
		data.setPoints(StringUtils.isEmpty(totalScore) ? 0 : Integer.valueOf(totalScore));
		data.setFieldGoal2ptAtt(StringUtils.isEmpty(totalFieldGoals2) ? 0 : Integer.valueOf(totalFieldGoals2.substring(totalFieldGoals2.indexOf('/') + 1)));
		data.setFieldGoal2ptMade(StringUtils.isEmpty(totalFieldGoals2) ? 0 : Integer.valueOf(totalFieldGoals2.substring(0, totalFieldGoals2.indexOf('/'))));
		data.setFieldGoal3ptAtt(StringUtils.isEmpty(totalFieldGoals3) ? 0 : Integer.valueOf(totalFieldGoals3.substring(totalFieldGoals3.indexOf('/') + 1)));
		data.setFieldGoal3ptMade(StringUtils.isEmpty(totalFieldGoals3) ? 0 : Integer.valueOf(totalFieldGoals3.substring(0, totalFieldGoals3.indexOf('/'))));
		data.setFreeThrow1ptAtt(StringUtils.isEmpty(totalFreeThrows) ? 0 : Integer.valueOf(totalFreeThrows.substring(totalFreeThrows.indexOf('/') + 1)));
		data.setFreeThrow1ptMade(StringUtils.isEmpty(totalFreeThrows) ? 0 : Integer.valueOf(totalFreeThrows.substring(0, totalFreeThrows.indexOf('/'))));
		data.setReboundsOff(StringUtils.isEmpty(totalOffensiveRebounds) ? 0 : Integer.valueOf(totalOffensiveRebounds));
		data.setReboundsDef(StringUtils.isEmpty(totalDefensiveRebounds) ? 0 : Integer.valueOf(totalDefensiveRebounds));
		data.setReboundsTotal(StringUtils.isEmpty(totalTotalRebounds) ? 0 : Integer.valueOf(totalTotalRebounds));
		data.setAssists(StringUtils.isEmpty(totalAssistances) ? 0 : Integer.valueOf(totalAssistances));
		data.setSteals(StringUtils.isEmpty(totalSteals) ? 0 : Integer.valueOf(totalSteals));
		data.setTurnovers(StringUtils.isEmpty(totalTurnovers) ? 0 : Integer.valueOf(totalTurnovers));
		data.setBlocksFavour(StringUtils.isEmpty(totalBlocksFavour) ? 0 : Integer.valueOf(totalBlocksFavour));
		data.setBlocksAgainst(StringUtils.isEmpty(totalBlocksAgainst) ? 0 : Integer.valueOf(totalBlocksAgainst));
		data.setFoulsCommited(StringUtils.isEmpty(totalFoulsCommited) ? 0 : Integer.valueOf(totalFoulsCommited));
		data.setFoulsReceived(StringUtils.isEmpty(totalFoulsReceived) ? 0 : Integer.valueOf(totalFoulsReceived));
		data.setIndex(StringUtils.isEmpty(totalValuation) ? 0 : Integer.valueOf(totalValuation));
		
		return data;
	}

//	private BoxScoreData extractBoxScoreData(Match match, Element boxHome, String entity, String venue) {
//		
//		BoxScoreData data = new BoxScoreData();
//		
//		Element box = null;
//		
//		if ("TEAM".equals(entity)) {
//			box = boxHome.getElementsByTag("tfoot").first();
//		} else if("PLAYER".equals(entity)) {
//			box = boxHome.getElementsByTag("tbody").first();
//		}
//		
//		Elements rows = box.getElementsByTag("tr");
//			
//		if ("TEAM".equals(entity)) {
//		rows.first();
//		} else if("PLAYER".equals(entity)) {
//			box = boxHome.getElementsByTag("tbody").first();
//		}
//		
//		Elements stats = box.getElementsByTag("td");
//		
//		String totalTimePlayed = box.getElementsByAttributeValueEnding("id", "TotalTimePlayed").text();
//		String totalScore = box.getElementsByAttributeValueEnding("id", "TotalScore").text();
//		String totalFieldGoals2 = box.getElementsByAttributeValueEnding("id", "TotalFieldGoals2").text();
//		String totalFieldGoals3 = box.getElementsByAttributeValueEnding("id", "TotalFieldGoals3").text();
//		String totalFreeThrows = box.getElementsByAttributeValueEnding("id", "TotalFreeThrows").text();
//		String totalOffensiveRebounds = box.getElementsByAttributeValueEnding("id", "TotalOffensiveRebounds").text();
//		String totalDefensiveRebounds = box.getElementsByAttributeValueEnding("id", "TotalDefensiveRebounds").text();
//		String totalTotalRebounds = box.getElementsByAttributeValueEnding("id", "TotalTotalRebounds").text();
//		String totalAssistances = box.getElementsByAttributeValueEnding("id", "TotalAssistances").text();
//		String totalSteals = box.getElementsByAttributeValueEnding("id", "TotalSteals").text();
//		String totalTurnovers = box.getElementsByAttributeValueEnding("id", "TotalTurnovers").text();
//		String totalBlocksFavour = box.getElementsByAttributeValueEnding("id", "TotalBlocksFavour").text();
//		String totalBlocksAgainst = box.getElementsByAttributeValueEnding("id", "TotalBlocksAgainst").text();
//		String totalFoulsCommited = box.getElementsByAttributeValueEnding("id", "TotalFoulsCommited").text();
//		String totalFoulsReceived = box.getElementsByAttributeValueEnding("id", "TotalFoulsReceived").text();
//		String totalValuation = box.getElementsByAttributeValueEnding("id", "TotalValuation").text();
//		
//		if ("TEAM".equals(entity)) {
//			if ("HOME".equals(venue)) {
//				data.setTeamId(match.getHomeTeam().getTeamId());
//				data.setTeamName(match.getHomeTeam().getTeamName());
//			} else {
//				data.setTeamId(match.getAwayTeam().getTeamId());
//				data.setTeamName(match.getAwayTeam().getTeamName());			
//			}
//			
//		} else if ("PLAYER".equals(entity)) {
//			
//		} else {
//			// TODO throw error
//		}
//		
//		
//		
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	

}
