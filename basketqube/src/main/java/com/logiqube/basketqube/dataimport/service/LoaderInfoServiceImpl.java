package com.logiqube.basketqube.dataimport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logiqube.basketqube.dataimport.repository.LoaderInfoRepository;
import com.logiqube.basketqube.model.LoaderInfo;

@Service
public class LoaderInfoServiceImpl implements LoaderInfoService {

	@Autowired
	LoaderInfoRepository loaderInfoRepository;
	
	@Override
	public LoaderInfo getLoaderInfo(String league) {
		return loaderInfoRepository.findByLeagueCode(league);
	}

	@Override
	public void saveLoaderInfo(LoaderInfo info) {
//		Optional<LoaderInfo> loaderInfo = Optional.ofNullable(getLoaderInfo(info.getLeagueCode()));
		loaderInfoRepository.save(info);
	}

}
