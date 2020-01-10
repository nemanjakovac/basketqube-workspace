package com.logiqube.basketqube.dataimport.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.logiqube.basketqube.model.Match;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {

}
