package com.easyfix.Jobs.model;

import org.codehaus.jackson.annotate.JsonProperty;


public class CustomerFeedback {

	private int id;
	
	private String happyWithService;
	
//	@JsonProperty("servicemen_rating")
	@JsonProperty("handymen_rating")
	private int EfrRating;	
	
	@JsonProperty("easyfix_rating")
	private int EfRating;
	
	
	
	public int getEfrRating() {
		return EfrRating;
	}
	public void setEfrRating(int efrRating) {
		EfrRating = efrRating;
	}
	public int getEfRating() {
		return EfRating;
	}
	public void setEfRating(int efRating) {
		EfRating = efRating;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHappyWithService() {
		return happyWithService;
	}
	public void setHappyWithService(String happyWithService) {
		this.happyWithService = happyWithService;
	}

}
