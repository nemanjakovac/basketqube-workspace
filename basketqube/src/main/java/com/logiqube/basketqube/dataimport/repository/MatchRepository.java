package com.logiqube.basketqube.dataimport.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.logiqube.basketqube.model.Match;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {

	Optional<Match> findByMatchId(Long valueOf);

}
