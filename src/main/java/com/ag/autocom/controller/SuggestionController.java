package com.ag.autocom.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ag.autocom.etl.JobCompletionNotificationListener;
import com.ag.autocom.exceptions.ApiRequestException;
import com.ag.autocom.service.SuggestionService;
import com.ag.autocom.service.dto.CityDto;

@RestController
@RequestMapping("api/search/")
public class SuggestionController {
	private static final Logger log = LoggerFactory.getLogger(SuggestionController.class);
	@Autowired
	SuggestionService suggestionService;

	@GetMapping("suggestions")
	public ResponseEntity<List<CityDto>> getSuggestionAll(@RequestParam Map<String, String> allParams) {
		//allParams.forEach((k, v) -> log.info(k + " : " + v));

		Optional<String> q = Optional.ofNullable(allParams.get("q"));

		if (!q.isPresent()) {
			throw new ApiRequestException("Query parameter 'q' must be in the request");

		}
		Optional<String> latitude = Optional.ofNullable(allParams.get("latitude"));
		Optional<String> longitude = Optional.ofNullable(allParams.get("longitude"));

		List<CityDto> foundCities = suggestionService.getSuggestionsAll(q.get(), latitude, longitude);
		return new ResponseEntity<>(foundCities, HttpStatus.OK);
	}

}
