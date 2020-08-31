package com.ag.autocom.service;

import java.util.List;

import com.ag.autocom.service.dto.CityDto;

public interface ISuggestionService {

	List<CityDto> findByNameStartingWith(String theWord);

	List<CityDto> findByNameContaining(String theWord);

}
