package com.logiqube.basketqube.dataimport.matches.factory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.logiqube.basketqube.dataimport.service.LoaderInfoService;
import com.logiqube.basketqube.dataimport.service.MatchService;
import com.logiqube.basketqube.enums.Enums.Entity;
import com.logiqube.basketqube.enums.Enums.Venue;
import com.logiqube.basketqube.model.BoxScore;
import com.logiqube.basketqube.model.BoxScoreData;
import com.logiqube.basketqube.model.Match;
import com.logiqube.basketqube.model.TeamScore;

@Component
public class MatchLoaderEuroleague extends MatchLoader {
	
	private String EL_BASE_URL = "https://www.euroleague.net";
	private String BOXSCORE_HOME_PAGE = "/main/results";

	private static String ROUND_PARAMETER = "?gamenumber=";
	private static String PHASE_PARAMETER = "&phasetypecode=";
	private static String SEASON_PARAMETER = "&seasoncode=";
	
	private static String LEAGUE_CODE = "EL";
	private static String LEAGUE_NAME = "Euroleague";
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, uuuu z: HH:mm");
	
	
	@Autowired
	public MatchLoaderEuroleague(MatchService matchService, LoaderInfoService loaderInfoService, Environment env) {
		setMatchServices(matchService, loaderInfoService, env);
	}

	@Override
	protected List<URL> getMatchUrls() {
		List<URL> urls = new ArrayList<>();

		Document doc = null;
		try {
			doc = Jsoup.connect(EL_BASE_URL.concat(BOXSCORE_HOME_PAGE)).get();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Element roundData = doc.getElementsByClass("round-header").first();

		extractCurrentData(roundData);

		// get minimum round from DB max (last) loaded, so we only load next rounds
		for (int i = Integer.valueOf(LAST_LOADED_ROUND) + 1; i < Integer.valueOf(ROUND_MAX); i++) {
			ROUND_CURRENT = String.valueOf(i);
			String roundPage = EL_BASE_URL.concat("/main/results")
					.concat(ROUND_PARAMETER).concat(ROUND_CURRENT)
					.concat(PHASE_PARAMETER).concat(PHASE_MAX)
					.concat(SEASON_PARAMETER).concat(SEASON_MAX);

			Document roundDoc = null;
			try {
				roundDoc = Jsoup.connect(roundPage).get();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

//			"div.wp-module-asidegames"
//			"div.livescore"
			Element matchList = roundDoc.getElementsByClass("livescore").last();

			Elements matches = matchList.select("a");

			matches.forEach(match -> {
				try {
					urls.add(new URL(EL_BASE_URL.concat(match.attr("href")).concat("#!boxscore")));
				} catch (MalformedURLException e) {
					// TODO handle exception
				}
			});

		}

		return urls;
	}

	@Override
	protected Match createMatch(URL url, Document doc) {
		
		Element gameInfo = doc.getElementById("sg-score");
		
//		Element gameScore = gameInfo.getElementsByClass("game-score").first();
		Element gameDate = gameInfo.getElementsByClass("dates").first();

		String leagueCode = LEAGUE_CODE;
		String leagueName = LEAGUE_NAME;
		
//		MultiValueMap<String, String> parameters = UriComponentsBuilder.fromHttpUrl(url.toString()).build().getQueryParams();	
//		String seasonCode = parameters.getFirst("seasoncode");
		String seasonCode =	url.toString().substring(url.toString().indexOf("seasoncode") + 12,
				url.toString().indexOf("seasoncode") + 16);
		String round = extractCurrentRound(doc.getElementsByClass("round-header").first().select("div > span:nth-child(3)").text());
		//matchUrl.substring(matchUrl.indexOf("gamecode") + 9, matchUrl.indexOf("seasoncode") - 1);

		LocalDateTime matchDate = extractMatchDate(gameDate);

		Match match = new Match(leagueCode, leagueName, seasonCode, matchDate, round, url.toString());

		return match;
	}

	@Override
	protected Match addScoreToMatch(Match match, Document doc) {
		
		Element gameInfo = doc.getElementById("sg-score");
		
		Element scoreByQuarter = doc.getElementsByClass("PartialsStatsByQuarterContainer").first();

		TeamScore homeTeam = setTeamData(gameInfo, scoreByQuarter, "HOME");

		TeamScore awayTeam = setTeamData(gameInfo, scoreByQuarter, "AWAY");

		match.setHomeTeam(homeTeam);
		
		match.setAwayTeam(awayTeam);
		
		return match;
	}

	@Override
	protected Match addBoxScoreToMatch(Match match, Document doc) {
		BoxScore boxScore = new BoxScore();
		match.setBoxScore(boxScore);
		
		processTeam(match, doc, Venue.HOME);
		processTeam(match, doc, Venue.AWAY);
		
		// TODO remove this
		addMatchTime(match);
		
		return match;
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
	
	private LocalDateTime extractMatchDate(Element gameDate) {

		String date = gameDate.getElementsByClass("date").first().html();
		return LocalDateTime.parse(date, formatter);
	}

	private void addMatchTime(Match match) {
		String time = match.getBoxScore().getHomeTeamBoxScoreData().getMinutesPlayed();
		int timeInt = Integer.valueOf(time.substring(0, time.indexOf(':')))/5;
		match.setMatchTime(timeInt);
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


}
