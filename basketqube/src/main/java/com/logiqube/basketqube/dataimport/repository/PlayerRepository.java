package com.logiqube.basketqube.dataimport.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.logiqube.basketqube.model.Player;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String>, PlayerRepositoryCustom {
	
	Player findByFirstNameAndLastName(String firstName, String lastName);

}
