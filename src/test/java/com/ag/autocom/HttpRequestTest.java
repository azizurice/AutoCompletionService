package com.ag.autocom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JsonContentAssert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ag.autocom.errors.ApiError;
import com.ag.autocom.service.dto.CityDto;

@SpringBootTest(classes = AutoCompletionServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shoudReturnListOfCityWhenValidInput() throws Exception {

		ResponseEntity<List<CityDto>> response = this.restTemplate.exchange(
				"http://localhost:" + port + "/suggestions?q=Londo", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CityDto>>() {
				});
		assertThat(response.getBody().size() > 0);
		assertNotNull(response.getBody());
	}
	
	

	
}


