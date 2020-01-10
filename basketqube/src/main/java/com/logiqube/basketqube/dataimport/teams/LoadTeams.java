package com.logiqube.basketqube.dataimport.teams;

import java.io.IOException;

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

		// select div with url to team details
		Elements teamList = doc.select("div.teams > div.item");

		teamList.forEach(team -> {
			try {
				loadTeam(team);
			} catch (Exception e) {
				// TODO: handle exception
			}
		});

	}

	private void loadTeam(Element teamData) {
		String leagueCode = "EL";
		String leagueName = "Euroleague";

		String code = teamData.select("div.RoasterName > a").attr("href");

		String teamCode = code.substring(code.indexOf("clubcode") + 9, code.indexOf("clubcode") + 12);
		String teamName = teamData.select("div.RoasterName > a").first().text();

		TeamDto teamDto = new TeamDto(leagueCode, leagueName, teamCode, teamName);

		teamDto = teamService.saveTeam(teamDto);

		if (log.isDebugEnabled())
			log.debug(String.format("Team %s saved!", teamDto));

	}

}
