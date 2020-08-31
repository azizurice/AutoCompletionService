package com.ag.autocom.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.ag.autocom.model.City;

public class CityItemProcessor implements ItemProcessor<City, City> {

	private static final Logger log = LoggerFactory.getLogger(CityItemProcessor.class);
	

	@Override
	public City process(final City city) throws Exception {
		
		
		final String name=city.getName();
		final String province=city.getProvince();
		final String country=city.getCountry();
		final String latitude = city.getLatitude();
		final String longitude = city.getLongitude();
		

		final City transformedCity = new City(name,province, country,latitude, longitude);

	//	log.info("Converting (" + city + ") into (" + transformedCity + ")");

		return transformedCity;
	}

}