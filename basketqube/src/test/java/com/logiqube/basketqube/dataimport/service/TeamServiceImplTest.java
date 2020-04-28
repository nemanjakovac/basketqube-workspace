package com.logiqube.basketqube.dataimport.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.logiqube.basketqube.dataimport.repository.TeamRepository;
import com.logiqube.basketqube.model.Team;

@RunWith(SpringRunner.class)
@SpringBootTest
class TeamServiceImplTest {

	@Autowired
	private TeamService teamService;
	
	@MockBean
	private TeamRepository teamRepository;
	
	@Test
	void deleteTeamTest() {
		Team team = new Team("Partizan");
		team.setTeamId(123);
		teamService.deleteTeam(String.valueOf(team.getTeamId()));
		verify(teamRepository, times(1)).deleteById(String.valueOf(team.getTeamId()));
	}

}
