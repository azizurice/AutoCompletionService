package com.ag.autocom.service;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ag.autocom.exceptions.ApiRequestException;
import com.ag.autocom.model.City;
import com.ag.autocom.model.ScoringParameter;
import com.ag.autocom.repository.SuggestionRepository;
import com.ag.autocom.service.dto.CityDto;
import com.ag.autocom.util.ScoreUtility;

@Service
public class SuggestionService implements ISuggestionService {
	private static final Logger log = LoggerFactory.getLogger(SuggestionService.class);

	@Autowired
	private SuggestionRepository suggestionRepository;

	public List<CityDto> getSuggestionsAll(String q, Optional<String> latitude, Optional<String> longitude) {

		List<CityDto> foundCities = new ArrayList<>();
		if (!latitude.isPresent() && !longitude.isPresent()) {
			foundCities.addAll(setupScore(findByNameStartingWith(q), q, 0.0, 0.0, false));

		} else if (latitude.isPresent() && longitude.isPresent()) {

			foundCities.addAll(setupScore(findByNameStartingWith(q), q, Double.parseDouble(latitude.get()),
					Double.parseDouble(longitude.get()), true));
		} else {
			throw new ApiRequestException("Query paramter 'latitude' and 'longitude' must be together in the requst");
		}
		foundCities.sort(Comparator.comparingDouble(CityDto::getScore).reversed());
		return foundCities;

	}

	private List<CityDto> setupScore(List<CityDto> foundCities, String q, double latitude, double longitude,
			boolean isLocationProvided) {
		if (foundCities.isEmpty()) {
			return foundCities;
		} else if (foundCities.size() == 1) {
			foundCities.get(0).setScore(1.0);
			return foundCities;
		} else {

			foundCities.forEach(e -> {
				if (isLocationProvided) {
					e.setUserDistance(ScoreUtility.getDistance(Double.parseDouble(e.getLatitude()),
							Double.parseDouble(e.getLongitude()), latitude, longitude));
				}
				e.setTextMatchingScore(ScoreUtility.textMatchingScore(q, e.getName()));

			});

			double longestDistanceCity = foundCities.stream().max(Comparator.comparing(CityDto::getUserDistance))
					.map(CityDto::getUserDistance).orElse(0.0);
			double shortestDistanceCity = foundCities.stream().min(Comparator.comparing(CityDto::getUserDistance))
					.map(CityDto::getUserDistance).orElse(0.0);

			double bestMatchingCity = foundCities.stream().max(Comparator.comparing(CityDto::getTextMatchingScore))
					.map(CityDto::getTextMatchingScore).orElse(0.0);
			double leastestMatchingCity = foundCities.stream().min(Comparator.comparing(CityDto::getTextMatchingScore))
					.map(CityDto::getTextMatchingScore).orElse(0.0);

			foundCities.forEach(e -> {
				double weightedNormalizedDistance = 0;
				if (isLocationProvided) {
					weightedNormalizedDistance = ScoringParameter.DISTANCE.getWeight() * ScoreUtility
							.normalizeScore(e.getUserDistance(), shortestDistanceCity, longestDistanceCity, 0.0, 1.0);
				}

				double weightedNormalizedTextMatching = ScoringParameter.TEXTMATCH.getWeight() * ScoreUtility
						.normalizeScore(e.getTextMatchingScore(), leastestMatchingCity, bestMatchingCity, 0.0, 1.0);

				e.setScore(weightedNormalizedDistance + weightedNormalizedTextMatching);

			});

			double higestScore = foundCities.stream().max(Comparator.comparing(CityDto::getScore))
					.map(CityDto::getScore).orElse(0.0);
			double lowestScore = foundCities.stream().min(Comparator.comparing(CityDto::getScore))
					.map(CityDto::getScore).orElse(0.0);

			foundCities.forEach(
					e -> e.setScore(ScoreUtility.normalizeScore(e.getScore(), lowestScore, higestScore, 0.01, 0.95)));

		}

		return foundCities;
	}

	@Override
	public List<CityDto> findByNameStartingWith(String theWord) {

		return suggestionRepository.findByNameStartingWith(theWord).stream().map(cityEntityToDtoMapper)
				.collect(Collectors.toList());

	}

	@Override
	public List<CityDto> findByNameContaining(String theWord) {

		return null;
	}

	Function<City, CityDto> cityEntityToDtoMapper = t -> new CityDto(t.getName(), t.getLatitude(), t.getLongitude());

}
