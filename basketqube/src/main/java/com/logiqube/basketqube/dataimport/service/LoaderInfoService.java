package com.logiqube.basketqube.dataimport.service;

import com.logiqube.basketqube.model.LoaderInfo;

public interface LoaderInfoService {
	
	LoaderInfo getLoaderInfo(String league);

	void saveLoaderInfo(LoaderInfo info);

}
