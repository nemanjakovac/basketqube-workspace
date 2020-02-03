package com.logiqube.basketqube.dataimport.matches.factory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dataimport.service.LoaderInfoService;
import com.logiqube.basketqube.dataimport.service.MatchService;
import com.logiqube.basketqube.enums.Enums.League;
import com.logiqube.basketqube.model.LoaderInfo;
import com.logiqube.basketqube.model.Match;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public abstract class MatchLoader {
	
	protected static String SEASON_MAX;
	protected static String PHASE_MAX;
	protected static String ROUND_MAX;
	
	protected static String ROUND_CURRENT;
	
	protected static String LAST_LOADED_ROUND;
	
	@Autowired
	protected static Environment env;
	
	protected static MatchService matchService;
	protected static LoaderInfoService loaderInfoService;
	
	// final keyword so subclass cant override setter
	protected static final void setMatchServices(MatchService matchService, LoaderInfoService loaderInfoService, Environment env) {
		MatchLoader.matchService = matchService;
		MatchLoader.loaderInfoService = loaderInfoService;
		MatchLoader.env = env;
	}

	// factory method design pattern
	public static MatchLoader getMatchLoader(League league) {
		switch (league) {
		case EL:
			return new MatchLoaderEuroleague(matchService, loaderInfoService, env);
		case SPAIN:
			return null;
		default:
			throw new IllegalArgumentException("Match Loader for this league doesn't exist!");
		}
	}
	
	protected abstract List<URL> getMatchUrls();
	
	protected abstract Match createMatch(URL url, Document doc);
	protected abstract Match addScoreToMatch(Match match, Document doc);	
	protected abstract Match addBoxScoreToMatch(Match match, Document doc);

	// template method design pattern
	public void loadAllMatches(String leagueCode) {
		LoaderInfo loader = loaderInfoService.getLoaderInfo(leagueCode);
		LAST_LOADED_ROUND = loader == null ? "0" : loader.getRound();
		List<URL> urls = getMatchUrls();
		if (urls.isEmpty()) {
			log.info(env.getProperty("matchLoader.nothingToLoad"));
			return;
		}
		int i = 0;
		Match lastLoadedMatch;
		for (URL url : urls) {
			lastLoadedMatch = loadSingleMatch(url);
			// if this is last url in list save info data
			if (i++ == urls.size() - 1) {
				loaderInfoService
						.saveLoaderInfo(new LoaderInfo(leagueCode, lastLoadedMatch.getRound(), LocalDateTime.now()));
				log.info(env.getProperty("matchLoader.matchesLoaded"), leagueCode, lastLoadedMatch.getRound());
			}
		}

	}

	private Match loadSingleMatch(URL url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url.toString()).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			throw new Exception("");
			e.printStackTrace();
		}
		
		Match match = createMatch(url, doc);
		addScoreToMatch(match, doc);
		addBoxScoreToMatch(match, doc);
		
		return matchService.saveMatch(match);
	}

}
