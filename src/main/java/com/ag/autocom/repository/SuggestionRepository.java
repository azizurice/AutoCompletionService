package com.ag.autocom.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.ag.autocom.model.City;

public interface SuggestionRepository extends CrudRepository<City, Long> {
	List<City> findByNameStartingWith(String theWord);
	// List<City> findByNameEndingWith(String ending);
	// List<City> findByNameContaining(String containing);
	// List<City> findByLatitudeAndLongitude(int latitdue, int longitude);

}
