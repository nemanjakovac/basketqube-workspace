package com.logiqube.basketqube.dataimport.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.logiqube.basketqube.model.Team;

@Repository
public interface TeamRepository extends MongoRepository<Team, String> {
	
	@Query(value = "{ 'league_data.league_code' : ?0, 'league_data.team_code' : ?1 }")
	Optional<Team> findByLeagueCodeAndTeamCode(String leagueCode, String teamCode);
	
	Optional<Team> findByTeamId(long teamId);

}
