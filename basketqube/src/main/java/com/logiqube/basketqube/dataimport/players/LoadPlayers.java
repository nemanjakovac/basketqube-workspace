package com.logiqube.basketqube.dataimport.players;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logiqube.basketqube.dataimport.service.PlayerService;
import com.logiqube.basketqube.dto.model.PlayerDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoadPlayers {

	private String EL_BASE_URL = "https://www.euroleague.net";
	private String EL_PLAYERS_URL = "https://www.euroleague.net/competition/players?letter=";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, uuuu");

	@Autowired
	PlayerService playerService;

	public void hello() {
		log.info("Hello load players");
	}

	public void load() throws IOException {

		// loop through all alphabet letters
		for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
			Document doc = Jsoup.connect(EL_PLAYERS_URL + alphabet).get();

			// select div with url to player details
			Elements playerLinks = doc
					.select("div.person-list.player-list > div.items-list > div.item > a:nth-child(1)");

			playerLinks.forEach(link -> {
				String text = link.html();
				try {
					loadPlayerFromUrl(EL_BASE_URL + link.attr("href"), text.substring(text.indexOf(',') + 1).trim(),
							text.substring(0, text.indexOf(',')).trim());
				} catch (IOException e) {
					// TODO handle exception
				}

				// put to sleep for 5 seconds after each player insert
				try {
//					Thread.sleep(5000);
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					// TODO handle exception
					log.debug("Sleeping for 5s");
				}
			});

		}

	}

	private void loadPlayerFromUrl(String url, String firstName, String lastName) throws IOException {
		if (checkIfPlayerExists(firstName, lastName)) {
			log.info("Player already exists!");
			return;
		}

		Document doc = Jsoup.connect(url).get();
		Element playerData = doc.selectFirst("div.player-data");

		PlayerDto player = new PlayerDto(firstName, lastName, extractDateOfBirth(playerData),
				extractNationality(playerData), extractHeight(playerData), extractPosition(playerData));

		player = playerService.savePlayer(player);
		if (log.isDebugEnabled())
			log.debug(String.format("Player %s saved!", player));
//		logger.info("sdsdsd");
	}

	private LocalDate extractDateOfBirth(Element playerData) {
		

		String dateOfBirth = playerData.selectFirst("div.summary-second > span:nth-child(2)").html();
		dateOfBirth = dateOfBirth.substring(dateOfBirth.indexOf(':') + 1).trim();

		return LocalDate.parse(dateOfBirth, formatter);
	}

	private double extractHeight(Element playerData) {
		String height = playerData.selectFirst("div.summary-second > span:nth-child(1)").html();
		return Double.valueOf(height.substring(height.indexOf(':') + 1).trim());
	}

	private String extractNationality(Element playerData) {
		String nationality = playerData.selectFirst("div.summary-second > span:nth-child(3)").html();
		return nationality.substring(nationality.indexOf(':') + 1).trim();
	}

	private String extractPosition(Element playerData) {
		return playerData.selectFirst("div.summary-first > span:nth-child(2) > span:nth-child(2)").html();
	}

	private boolean checkIfPlayerExists(String firstName, String lastName) {
		PlayerDto player = playerService.getByFirstNameAndLastName(firstName, lastName);
		return player != null;
	}

}
