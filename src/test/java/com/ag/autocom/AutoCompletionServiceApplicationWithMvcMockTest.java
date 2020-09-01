package com.ag.autocom;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AutoCompletionServiceApplicationWithMvcMockTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void whenValidInput_thenReturns200() throws Exception {
		this.mockMvc.perform(get("/suggestions?q=Losndo")).andExpect(status().isOk());

	}

	@Test
	public void whenInvalidInput_thenReturns400() throws Exception {
		this.mockMvc.perform(get("/suggestions?qq=Losndo")).andExpect(status().isBadRequest());

	}

	@Test
	public void shouldReturnJsonTypeForValidInput() throws Exception {
		this.mockMvc.perform(get("/suggestions?q=Losndo")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	public void shouldReturnJsonTypeForInvalidInput() throws Exception {
		this.mockMvc.perform(get("/suggestions?qq=Losndo")).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}



}
