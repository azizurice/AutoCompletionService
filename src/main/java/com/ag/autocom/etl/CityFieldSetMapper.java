package com.ag.autocom.etl;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.ag.autocom.model.City;



class CityFieldSetMapper implements FieldSetMapper<City> {

	@Override
	public City mapFieldSet(FieldSet fieldSet) throws BindException {
		final City city = new City();
		
		city.setName(fieldSet.readString("name")+", "+fieldSet.readString("province")+", "+fieldSet.readString("country"));
		city.setProvince(fieldSet.readString("province"));
		city.setCountry(fieldSet.readString("country"));
		city.setLatitude(fieldSet.readString("latitude"));
		city.setLongitude(fieldSet.readString("longitude"));

		return city;
	}
}