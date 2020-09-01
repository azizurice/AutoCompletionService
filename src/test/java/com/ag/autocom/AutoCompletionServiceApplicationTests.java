package com.ag.autocom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;




import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.ag.autocom.controller.SuggestionController;



@SpringBootTest(classes = AutoCompletionServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AutoCompletionServiceApplicationTests {

	@Autowired
	private SuggestionController controller;

	@Test
	public void contexLoads() throws Exception {
		Assertions.assertThat(controller).isNotNull();

	}
}