package com.logiqube.basketqube.dataimport.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.logiqube.basketqube.model.LoaderInfo;

@Repository
public interface LoaderInfoRepository extends MongoRepository<LoaderInfo, String> {
	
	LoaderInfo findByLeagueCode(String leagueCode);

}
