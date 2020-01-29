package com.logiqube.basketqube.dataimport.matches.factory;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dataimport.service.MatchService;
import com.logiqube.basketqube.enums.Enums.League;
import com.logiqube.basketqube.model.Match;

@Component
public abstract class MatchLoader {
	
	protected static String SEASON_MAX;
	protected static String PHASE_MAX;
	protected static String ROUND_MAX;
	
	protected static String ROUND_CURRENT;
	

	protected static MatchService matchService;
	
	// final keyword so subclass cant override setter
	protected static final void setMatchService(MatchService matchService) {
		MatchLoader.matchService = matchService;
	}

	// factory method design pattern
	public static MatchLoader getMatchLoader(League league) {
		switch (league) {
		case EL:
			return new MatchLoaderEuroleague(matchService);
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
	public void loadAllMatches() {
		List<URL> urls = getMatchUrls();
		urls.stream().forEach(url -> {
			loadSingleMatch(url);
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO handle exception
//				log.debug("Sleeping for 10s");
			}
		});
	}

	private void loadSingleMatch(URL url) {
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
		
		matchService.saveMatch(match);
	}

}
