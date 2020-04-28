package com.logiqube.basketqube.dataimport.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.logiqube.basketqube.dataimport.repository.PlayerRepository;
import com.logiqube.basketqube.dto.mapper.PlayerMapper;
import com.logiqube.basketqube.dto.model.PlayerDto;
import com.logiqube.basketqube.model.Player;

@RunWith(SpringRunner.class)
@SpringBootTest
class PlayerServiceImplTest {
	
	@Autowired
	private PlayerService playerService;
	
	@MockBean
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerMapper playerMapper;
	
//    @BeforeAll
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }

//    @Test
//    public void givenPlayerServiceWhenQueriedWithAnIdThenGetExpectedPlayer() {
//        Player player = new Player("Nemanja", "Kovac", LocalDate.now(), "Serbian", 1.82, "SG");
//
////        Mockito.when(playerRepository.findByPlayerId(100L))
////          .thenReturn(Optional.ofNullable(player));
//  
//        PlayerDto result = playerService.getByFirstNameAndLastName("Nemanja", "Kovac");
//  
//        assertEquals(player.getFirstName(), result.getFirstName());
//    }
	
	@Test
	public void getPlayersTest() {
		when(playerRepository.findAll()).thenReturn(Stream.of(
				new Player("Nemanja", "Kovac", LocalDate.now(), "Serbian", 1.82, "SG"), 
				new Player("Petar", "Petrovic", LocalDate.now(), "Serbian", 2.12, "C")).collect(Collectors.toList()));
		assertEquals(2, playerService.getAll().size());
	}
	
	@Test
	public void getByFirstNameAndLastName() {
		String firstName = "Nemanja", lastName = "Kovac";
		when(playerRepository.findByFirstNameAndLastName(firstName, lastName))
			.thenReturn(Optional.of(new Player("Nemanja", "Kovac", LocalDate.now(), "Serbian", 1.82, "SG")));
		assertEquals(firstName, playerService.getByFirstNameAndLastName(firstName, lastName).getFirstName());
	}

	@Test
	public void createPlayerTest() {
		PlayerDto playerDto = new PlayerDto("Nemanja", "Kovac");
		when(playerRepository.save(playerMapper.convertToEntity(playerDto)))
		.thenReturn(playerMapper.convertToEntity(playerDto));
		assertEquals(playerDto, playerService.savePlayer(playerDto));
	}
}
