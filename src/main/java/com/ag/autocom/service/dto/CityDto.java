package com.ag.autocom.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "userDistance","textMatchingScore" })
public class CityDto {
	private String name;
	private String latitude;
	private String longitude;
	private double userDistance;
	private double textMatchingScore;
	private double score;

	public CityDto() {
	}

	public CityDto(String name, String latitude, String longitude) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public double getUserDistance() {
		return userDistance;
	}

	public void setUserDistance(double userDistance) {
		this.userDistance = userDistance;
	}

	public double getTextMatchingScore() {
		return textMatchingScore;
	}

	public void setTextMatchingScore(double textMatchingScore) {
		this.textMatchingScore = textMatchingScore;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "CityDto [name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + ", userDistance="
				+ userDistance + ", textMatchingScore=" + textMatchingScore + ", score=" + score + "]";
	}
	
	

}
