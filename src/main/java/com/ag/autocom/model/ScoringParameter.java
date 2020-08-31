package com.ag.autocom.model;

public enum ScoringParameter {
	DISTANCE(7), TEXTMATCH(3);

	int weight;

	private ScoringParameter(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

}
