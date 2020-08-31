package com.ag.autocom.exceptions;

public class SuggestionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SuggestionNotFoundException(String cityName) {
		super("Suggestion for " + cityName + " not found.");
	}

}