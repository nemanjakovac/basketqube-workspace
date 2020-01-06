package com.logiqube.basketqube.dataimport.teams;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dataimport.service.TeamService;
import com.logiqube.basketqube.dto.model.TeamDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoadTeams {

	@Autowired
	TeamService teamService;

	public void hello() {
		log.info("Hello load teams");
	}

	public void load() throws IOException {

		// load teams page
		Document doc = Jsoup.connect("https://www.euroleague.net/competition/teams").get();

		// select div with url to player details
		Elements teamList = doc.select("div.teams > div.item");
		
		teamList.forEach(team -> {
			loadTeam(team);
		}
		);

//		playerLinks.forEach(link -> {
//			String text = link.html();
//			try {
//				loadPlayerFromUrl(EL_BASE_URL + link.attr("href"), text.substring(text.indexOf(',') + 1).trim(),
//						text.substring(0, text.indexOf(',')).trim());
//			} catch (IOException e) {
//				// TODO handle exception
//			}
//
//			// put to sleep for 5 seconds after each player insert
//			try {
////					Thread.sleep(5000);
//				TimeUnit.SECONDS.sleep(5);
//			} catch (InterruptedException e) {
//				// TODO handle exception
//				log.debug("Sleeping for 5s");
//			}
//		});

	}

	private void loadTeam(Element teamData) {
		String leagueCode = "EL";
		String leagueName = "Euroleague";
		Element code = teamData.select("div.RoasterName > a").first();
		String teamCode = teamData.select("div.RoasterName > a").attr("href");
		
		String a = "";
		
//		TeamDto teamDto = new TeamDto(leagueCode, leagueName, teamCode, teamName);
		// TODO Auto-generated method stub
		
	}

}
